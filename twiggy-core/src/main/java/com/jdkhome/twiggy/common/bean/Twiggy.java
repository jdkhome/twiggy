package com.jdkhome.twiggy.common.bean;

import java.lang.annotation.*;

/**
 * create by linkji.
 * create at 10:27 2019-11-29
 * 权限实体
 * 鉴权标记注解
 * 将该注解标记在Controller层的方法上 标记这个接口需要鉴权
 * <p>
 * 对于一个权限实体 只有访问者同时拥有功能权限和数据权限才可使用
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Twiggy {

    /**
     * 权限实体名称
     *
     * @return
     */
    String value() default "未命名";

    /**
     * 是否可作为菜单
     * (兼容服务端渲染页面的鉴权方式)
     *
     * @return
     */
    boolean menu() default false;

    /**
     * 功能权限表达式
     * <p>
     * eg. (merchangt >= 1) && ((user >= 3) || (xxx == 5))
     *
     * @return
     */
    String fun() default "";

    /**
     * 数据权限表达式
     * 这里只能对请求资源进行鉴权
     * <p>
     * 查询到的数据进行细致鉴权需要使用另外的接口
     * <p>
     * eg. (accountNo || userId)
     *
     * @return
     */
    String res() default "";

}
