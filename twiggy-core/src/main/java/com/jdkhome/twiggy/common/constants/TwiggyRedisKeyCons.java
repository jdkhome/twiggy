package com.jdkhome.twiggy.common.constants;

/**
 * create by linkji.
 * create at 11:23 2019-12-09
 */
public interface TwiggyRedisKeyCons {

    /**
     * 权限实体
     * redis中Hash存储
     * key : uri
     * val : JSON(TwiggyAuthBean)
     */
    String AUTH_BEAN = "twiggy:auth_bean";

    /**
     * 授权实体
     */
    String AUTHORIZED_ENTITY = "twiggy:authorized_entity:%s";

    static String formatAuthorizedEntityKey(String token) {
        return String.format(AUTHORIZED_ENTITY, token);
    }

    /**
     * 虚拟凭证
     * val : 永久授权的token
     */
    String VIRTUAL_TOKEN = "twiggy:virtual_token:%s";

    static String formatVirtualTokenKey(String vToken) {
        return String.format(VIRTUAL_TOKEN, vToken);
    }

    /**
     * 虚拟凭证映射
     * 避免使用keys命令
     * redis中Set存储
     * key: twiggy:virtual_token_map:{token}
     * val: [{vToken}]
     */
    String VIRTUAL_TOKEN_MAP = "twiggy:virtual_token_map:%s";

    static String formatVirtualTokenMapKey(String token) {
        return String.format(VIRTUAL_TOKEN_MAP, token);
    }
}
