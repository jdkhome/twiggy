package com.jdkhome.twiggy.core.authorize.impl;

import com.jdkhome.twiggy.common.bean.AuthorizedEntity;
import com.jdkhome.twiggy.common.constants.TwiggyRedisKeyCons;
import com.jdkhome.twiggy.common.constants.TwiggySystemCons;
import com.jdkhome.twiggy.core.authorize.TwiggyAuthorizeService;
import com.jdkhome.twiggy.core.authorize.TwiggyVirtualTokenService;
import com.jdkhome.twiggy.utils.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * create by linkji.
 * create at 14:57 2019-12-11
 */
@Slf4j
@Service
public class TwiggyVirtualTokenServiceImpl implements TwiggyVirtualTokenService {

    @Autowired
    @Qualifier("twiggyRedisTemplate")
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    TwiggyAuthorizeService twiggyAuthorizeService;

    /**
     * 获取虚拟凭证
     *
     * @param token   永久授权的token
     * @param timeout 过期时间 单位是分
     * @param single  是否单点(如果为true 将会移除其他的虚拟授权)
     * @return
     */
    @Override
    public String getVirtualToken(String token, Long timeout, boolean single) {
        if (single) {
            // 移除其他临时凭证
            this.delAllVirtualToken(token);
        }

        String vToken = UUIDGenerator.get();

        // 记录到永久授权->虚拟凭证 映射
        // 这个映射无法自动过期 暂时只能在删除所有虚拟凭证时清理
        stringRedisTemplate.opsForSet().add(TwiggyRedisKeyCons.formatVirtualTokenMapKey(token), vToken);

        // 保存授权
        stringRedisTemplate.opsForValue()
                .set(TwiggyRedisKeyCons.formatVirtualTokenKey(vToken), token, timeout, TimeUnit.MINUTES);

        return vToken;
    }

    /**
     * 删除所有虚拟凭证
     *
     * @param token 永久授权的token
     */
    @Override
    public void delAllVirtualToken(String token) {

        String vTokenMapKey = TwiggyRedisKeyCons.formatVirtualTokenMapKey(token);

        Set<String> vTokens = stringRedisTemplate.opsForSet().members(vTokenMapKey);

        if (vTokens != null) {
            stringRedisTemplate.delete(vTokens.stream().map(TwiggyRedisKeyCons::formatVirtualTokenKey).collect(Collectors.toSet()));
            stringRedisTemplate.delete(vTokenMapKey);
        }
    }

    /**
     * 删除虚拟凭证
     *
     * @param vToken 虚拟token
     */
    @Override
    public void delVirtualToken(String vToken) {

        if (vToken == null || vToken.length() == 0) {
            return;
        }

        String token = stringRedisTemplate.opsForValue().get(TwiggyRedisKeyCons.formatVirtualTokenKey(vToken));

        // 删除授权
        stringRedisTemplate.delete(stringRedisTemplate.keys(TwiggyRedisKeyCons.formatVirtualTokenKey(vToken)));

        // 删除授权映射
        stringRedisTemplate.opsForSet().remove(TwiggyRedisKeyCons.formatVirtualTokenMapKey(token), vToken);

    }

    /**
     * 删除虚拟凭证
     * 从线程的Request中获取虚拟凭证并删除
     */
    @Override
    public void delVirtualToken() {
        // 获取Request信息
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();

        String vToken = request.getHeader(TwiggySystemCons.TOKEN_NAME);

        this.delVirtualToken(vToken);
    }

    /**
     * 通过虚拟凭证获取授权实体
     *
     * @param vToken
     * @return
     */
    @Override
    public AuthorizedEntity getAuthorizedEntityByVirtualToken(String vToken) {
        String token = stringRedisTemplate.opsForValue().get(TwiggyRedisKeyCons.formatVirtualTokenKey(vToken));
        return twiggyAuthorizeService.getAuthorizedEntity(token);
    }
}
