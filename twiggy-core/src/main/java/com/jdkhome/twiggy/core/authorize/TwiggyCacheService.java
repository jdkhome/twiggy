package com.jdkhome.twiggy.core.authorize;

import com.jdkhome.twiggy.common.bean.TwiggyAuthBean;

import java.util.List;
import java.util.Map;

/**
 * create by linkji.
 * create at 11:20 2019-12-09
 * 缓存业务接口
 */
public interface TwiggyCacheService {

    /**
     * 重置所有权限实体
     *
     * @param authBeans
     */
    void resetAllAuthBean(List<TwiggyAuthBean> authBeans);

    /**
     * 获取所有权限实体
     */
    Map<String, TwiggyAuthBean> getAllAuthBean();

    /**
     * 获取权限实体
     *
     * @param uri
     * @return
     */
    TwiggyAuthBean getAuthBean(String uri);

}
