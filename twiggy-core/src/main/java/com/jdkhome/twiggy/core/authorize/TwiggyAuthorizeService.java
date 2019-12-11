package com.jdkhome.twiggy.core.authorize;

import com.jdkhome.twiggy.common.bean.AuthorizedEntity;

/**
 * create by linkji.
 * create at 12:19 2019-12-11
 * 授权业务接口
 */
public interface TwiggyAuthorizeService {

    /**
     * 向下刷新授权实体
     *
     * @param token
     */
    void refreshAuthDown(String token);

    /**
     * 获取授权实体
     *
     * @param token
     * @return
     */
    AuthorizedEntity getAuthorizedEntity(String token);

}
