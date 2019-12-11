package com.jdkhome.twiggy.core.authorize.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdkhome.twiggy.common.bean.AuthorizedEntity;
import com.jdkhome.twiggy.common.bean.TwiggyAuthBean;
import com.jdkhome.twiggy.common.constants.TwiggyRedisKeyCons;
import com.jdkhome.twiggy.common.enums.TwiggyTokenTypeEnum;
import com.jdkhome.twiggy.core.authentication.fun.FunExpression;
import com.jdkhome.twiggy.core.authorize.TwiggyAuthorizeService;
import com.jdkhome.twiggy.core.authorize.TwiggyCacheService;
import com.jdkhome.twiggy.generator.model.*;
import com.jdkhome.twiggy.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * create by linkji.
 * create at 14:23 2019-12-11
 */
@Slf4j
@Service
public class TwiggyAuthorizeServiceImpl implements TwiggyAuthorizeService {

    @Autowired
    @Qualifier("twiggyRedisTemplate")
    StringRedisTemplate stringRedisTemplate;

    @Value("${twiggy.timeout}")
    private int timeout;

    @Autowired
    TwiggyCacheService twiggyCacheService;

    @Autowired
    TwiggyTokenService twiggyTokenService;

    @Autowired
    TwiggyRoleService twiggyRoleService;

    @Autowired
    TwiggyResGrantService twiggyResGrantService;

    @Autowired
    TwiggyFunGroupService twiggyFunGroupService;

    @Autowired
    TwiggyResGroupService twiggyResGroupService;

    /**
     * 向下刷新授权实体
     *
     * @param token
     */
    @Override
    public void refreshAuthDown(String token) {
        // 重新计算当前的授权实体
        this.calcAuthorizedEntity(token);

        // 重新计算由当前token创建的子token的授权实体 递归
        twiggyTokenService.getAllToken(null, token, null)
                .stream().map(TwiggyToken::getToken).forEach(this::refreshAuthDown);
    }


    /**
     * 获取授权实体
     *
     * @param token
     * @return
     */
    @Override
    public AuthorizedEntity getAuthorizedEntity(String token) {

        if (token == null || token.length() == 0) {
            return null;
        }

        // 先尝试从缓存中获取
        String val = stringRedisTemplate.opsForValue().get(TwiggyRedisKeyCons.formatAuthorizedEntityKey(token));
        if (val != null && val.length() > 0) {
            return JSONObject.parseObject(val, AuthorizedEntity.class);
        }

        // 缓存中没有则重新计算并缓存
        return this.calcAuthorizedEntity(token);
    }

    /**
     * 计算并缓存授权实体
     *
     * @param token
     * @return
     */
    private AuthorizedEntity calcAuthorizedEntity(String token) {
        AuthorizedEntity result = new AuthorizedEntity();
        result.setToken(token);

        TwiggyToken thisToken = twiggyTokenService.getToken(token);
        if (thisToken == null) {
            return null;
        }

        if (TwiggyTokenTypeEnum.SUPER.getCode() == thisToken.getType()) {
            // 根据 角色 和 等级 授予功能权限
            Set<String> funAuthSet = new HashSet<>();

            Map<String, Integer> roleLevelMap = new HashMap<>();
            twiggyRoleService.getAllRoleToken(null, token, null)
                    .forEach(roleToken -> roleLevelMap.put(roleToken.getRoleKey(), roleToken.getLevel()));

            // 根据扫描到的所有功能权限 来授权
            Map<String, TwiggyAuthBean> allFun = twiggyCacheService.getAllAuthBean();

            allFun.forEach((k, v) -> {
                String funExpression = v.getFun();
                if (funExpression.length() == 0) {
                    // 功能权限表达式为空 直接获得权限
                    funAuthSet.add(k);
                } else if (FunExpression.calcExp(v.getFun(), roleLevelMap)) {
                    // 功能权限表达式不为空, 根据表达式判断是否有权限
                    funAuthSet.add(k);
                }
            });

            Map<String, Set<String>> resAuthMap = new HashMap<>();

            // 数据权限授权
            twiggyResGrantService.getAllResGrant(token, null)
                    .stream().forEach(tokenGrant -> {
                if (!resAuthMap.containsKey(tokenGrant.getResKey())) {
                    resAuthMap.put(tokenGrant.getResKey(), new HashSet<>());
                }
                resAuthMap.get(tokenGrant.getResKey()).add(tokenGrant.getResVal());
            });

            result.setFunAuthSet(funAuthSet);
            result.setResAuthMap(resAuthMap);

        } else {

            Set<String> superTokens = new HashSet<>();
            String superToken = thisToken.getSuperToken();
            do {
                superTokens.add(superToken);
                superToken = twiggyTokenService.getToken(superToken).getSuperToken();
            } while (superToken != null && superToken.length() > 0);


            // 根据加入的权限组 授予功能权限
            Set<String> funAuthSet = new HashSet<>();

            // token 加入的 权限组 | 只能够从super 或者 super的super 或更高级 继承到权限
            Set<TwiggyFunGroup> funGroups = twiggyFunGroupService.getAllGroupToken(null, token)
                    .stream().map(TwiggyFunGroupToken::getGroupNo)
                    .map(twiggyFunGroupService::getGroup)
                    .filter(funGroup -> superTokens.contains(funGroup.getCreateToken()))
                    .collect(Collectors.toSet());

            funGroups.forEach(funGroup -> {
                // 权限组中包含的权限
                Set<String> funKey = twiggyFunGroupService.getAllGroupVal(funGroup.getGroupNo(), null)
                        .stream().map(TwiggyFunGroupVal::getFunKey).collect(Collectors.toSet());

                // 权限组创建者拥有的权限
                Set<String> createTokenFunKey = this.getAuthorizedEntity(funGroup.getCreateToken()).getFunAuthSet();

                // 有效权限 -> 组权限与创建者权限 取交集
                Set<String> validFunKey = new HashSet<>(funKey);
                validFunKey.retainAll(createTokenFunKey);

                funAuthSet.addAll(validFunKey);
            });

            // 根据加入的资源组 授予数据权限
            Map<String, Set<String>> resAuthMap = new HashMap<>();

            // token 加入的 资源组 ｜ 只能够从super 或者 super的super 或更高级 继承到资源
            Set<TwiggyResGroup> resGroups = twiggyResGroupService.getAllGroupToken(null, token)
                    .stream().map(TwiggyResGroupTokenKey::getGroupNo)
                    .map(twiggyResGroupService::getGroup)
                    .filter(resGroup -> superTokens.contains(resGroup.getCreateToken()))
                    .collect(Collectors.toSet());

            resGroups.forEach(resGroup -> {
                // 资源组中包含的资源
                Map<String, Set<String>> resGroupVals = new HashMap<>();
                twiggyResGroupService.getAllGroupVal(resGroup.getGroupNo(), null)
                        .stream().forEach(resGroupVal -> {
                    if (!resGroupVals.containsKey(resGroupVal.getResKey())) {
                        resGroupVals.put(resGroupVal.getResKey(), new HashSet<>());
                    }
                    resGroupVals.get(resGroupVal.getResKey()).add(resGroupVal.getResVal());
                });

                // 资源组创建者拥有的资源
                Map<String, Set<String>> createTokenResVals = this.getAuthorizedEntity(resGroup.getCreateToken()).getResAuthMap();

                Map<String, Set<String>> validResVal = new HashMap<>();

                // 取交集得到 有效的key
                Set<String> validKeys = new HashSet<>(resGroupVals.keySet());
                validKeys.retainAll(createTokenResVals.keySet());
                validKeys.forEach(key -> {
                    // 取交集得到 有效的val
                    Set<String> validVals = new HashSet<>(resGroupVals.get(key));
                    validVals.retainAll(createTokenResVals.get(key));

                    if (validKeys.size() > 0) {
                        // 交集部分才是有效继承的资源
                        validResVal.put(key, validVals);
                    }
                });


                // 将继承到有效的资源 加入数据权限map
                validResVal.forEach((k, vs) -> {
                    if (resAuthMap.containsKey(k)) {
                        resAuthMap.get(k).addAll(vs);
                    } else {
                        resAuthMap.put(k, vs);
                    }
                });
            });

            result.setFunAuthSet(funAuthSet);
            result.setResAuthMap(resAuthMap);
        }

        stringRedisTemplate.opsForValue().set(TwiggyRedisKeyCons.formatAuthorizedEntityKey(result.getToken()), JSONObject.toJSONString(result), timeout, TimeUnit.MINUTES);
        return result;
    }
}
