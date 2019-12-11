package com.jdkhome.twiggy.core.authentication.res;

/**
 * create by linkji.
 * create at 11:43 2019-12-05
 * 使用者需要用一个枚举类实现这个接口，以定义所有数据资源
 */
public interface TwRes {

    /**
     * 数据资源Key
     *
     * @return
     */
    String getKey();

    /**
     * 数据资源名称
     *
     * @return
     */
    String getName();
}
