package com.jdkhome.twiggy.common.bean;

import lombok.Data;

/**
 * create by linkji.
 * create at 14:42 2019-12-10
 * 权限实体
 */
@Data
public class TwiggyAuthBean {

    /**
     * 接口uri
     */
    String uri;

    /**
     * 权限实体名称
     */
    String name;

    /**
     * 是否可作为菜单
     */
    Boolean menu;

    /**
     * 功能权限表达式
     */
    String fun;

    /**
     * 资源权限表达式
     */
    String res;

    public TwiggyAuthBean(){}

    public TwiggyAuthBean(String uri, Twiggy twiggy) {
        this.uri = uri;
        this.name = twiggy.value();
        this.menu = twiggy.menu();
        this.fun = twiggy.fun();
        this.res = twiggy.res();
    }

}
