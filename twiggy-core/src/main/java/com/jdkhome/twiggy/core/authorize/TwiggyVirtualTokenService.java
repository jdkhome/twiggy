package com.jdkhome.twiggy.core.authorize;

import com.jdkhome.twiggy.common.bean.AuthorizedEntity;

/**
 * create by linkji.
 * create at 12:20 2019-12-11
 * 临时凭证业务接口
 */
public interface TwiggyVirtualTokenService {

    /**
     * 获取虚拟凭证
     *
     * @param token   永久授权的token
     * @param timeout 过期时间 单位是分
     * @param single  是否单点(如果为true 将会移除其他的虚拟授权)
     * @return
     */
    String getVirtualToken(String token, Long timeout, boolean single);

    /**
     * 删除所有虚拟凭证
     *
     * @param token 永久授权的token
     */
    void delAllVirtualToken(String token);

    /**
     * 删除虚拟凭证
     *
     * @param vToken 虚拟token
     */
    void delVirtualToken(String vToken);

    /**
     * 删除虚拟凭证
     * 从线程的Request中获取虚拟凭证并删除
     */
    void delVirtualToken();

    /**
     * 通过虚拟凭证获取授权实体
     *
     * @param vToken
     * @return
     */
    AuthorizedEntity getAuthorizedEntityByVirtualToken(String vToken);

}
