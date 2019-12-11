package com.jdkhome.twiggy.generator.model;

public class TwiggyRoleTokenKey {
    private String token;

    private String roleKey;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getRoleKey() {
        return roleKey;
    }

    public void setRoleKey(String roleKey) {
        this.roleKey = roleKey == null ? null : roleKey.trim();
    }
}