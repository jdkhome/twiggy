package com.jdkhome.twiggy.core.authorize.impl;

import com.alibaba.fastjson.JSONObject;
import com.jdkhome.twiggy.core.authorize.TwiggyCacheService;
import com.jdkhome.twiggy.common.bean.TwiggyAuthBean;
import com.jdkhome.twiggy.common.constants.TwiggyRedisKeyCons;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create by linkji.
 * create at 11:26 2019-12-09
 */
@Slf4j
@Service
public class TwiggyCacheServiceImpl implements TwiggyCacheService {

    @Autowired
    @Qualifier("twiggyRedisTemplate")
    StringRedisTemplate stringRedisTemplate;

    @Value("${twiggy.timeout}")
    private int timeout;

    /**
     * 重置所有权限实体
     *
     * @param authBeans
     */
    @Override
    public void resetAllAuthBean(List<TwiggyAuthBean> authBeans) {

        Map<String, TwiggyAuthBean> authBeanMap = new HashMap<>();
        authBeans.forEach(authBean -> authBeanMap.put(authBean.getUri(), authBean));

        // 保存至redis
        authBeanMap.forEach((k, v) -> {
            stringRedisTemplate.opsForHash().put(TwiggyRedisKeyCons.AUTH_BEAN, k, JSONObject.toJSONString(v));
            log.info("[Twiggy] => registry {}:{}", v.getName(), v.getUri());
        });

        Set<String> allNowKeys = stringRedisTemplate.opsForHash().keys(TwiggyRedisKeyCons.AUTH_BEAN)
                .stream().map(Object::toString).collect(Collectors.toSet());
        allNowKeys.removeAll(authBeanMap.keySet());
        allNowKeys.forEach(key -> stringRedisTemplate.opsForHash().delete(TwiggyRedisKeyCons.AUTH_BEAN, key));

        // 清空所有授权实体
        Set<String> authorizedKeys = stringRedisTemplate.keys(TwiggyRedisKeyCons.formatAuthorizedEntityKey("*"));
        if (authorizedKeys != null && authorizedKeys.size() > 0) {
            authorizedKeys.forEach(key -> stringRedisTemplate.delete(key));
        }

    }

    /**
     * 获取所有权限实体
     */
    @Override
    public Map<String, TwiggyAuthBean> getAllAuthBean() {

        Map<String, TwiggyAuthBean> result = new HashMap<>();

        stringRedisTemplate.opsForHash().values(TwiggyRedisKeyCons.AUTH_BEAN)
                .stream()
                .map(Object::toString)
                .map(val -> JSONObject.parseObject(val, TwiggyAuthBean.class))
                .forEach(authBean -> result.put(authBean.getUri(), authBean));

        return result;
    }

    /**
     * 获取权限实体
     *
     * @param uri
     * @return
     */
    @Override
    public TwiggyAuthBean getAuthBean(String uri) {

        String val = (String) stringRedisTemplate.opsForHash().get(TwiggyRedisKeyCons.AUTH_BEAN, uri);
        if (val != null && val.length() > 0) {
            return JSONObject.parseObject(val, TwiggyAuthBean.class);
        }
        return null;
    }

}
