package com.jdkhome.twiggy.common.bean;

import java.lang.annotation.*;

@Documented
@Inherited
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TwiggyRes {

    /**
     * 代表资源Key 如果为空 则取变量名做为资源Key
     *
     * @return
     */
    String value() default "";
}