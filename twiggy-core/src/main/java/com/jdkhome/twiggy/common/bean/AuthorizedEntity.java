package com.jdkhome.twiggy.common.bean;

import lombok.Data;

import java.util.Map;
import java.util.Set;

/**
 * create by linkji.
 * create at 11:26 2019-12-06
 * 授权实体
 */
@Data
public class AuthorizedEntity {

    /**
     * 被授权的token
     */
    String token;

    /**
     * 有授权的功能权限集合
     * 内容是uri
     */
    Set<String> funAuthSet;

    /**
     * 有授权的资源权限集合
     * key : 资源类型 比如 AccountNo
     * value : 资源内容 ["A000001","A000002"]
     */
    Map<String, Set<String>> resAuthMap;

}
