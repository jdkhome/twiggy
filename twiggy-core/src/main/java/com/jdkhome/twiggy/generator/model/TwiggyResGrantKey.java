package com.jdkhome.twiggy.generator.model;

public class TwiggyResGrantKey {
    private String token;

    private String resKey;

    private String resVal;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getResKey() {
        return resKey;
    }

    public void setResKey(String resKey) {
        this.resKey = resKey == null ? null : resKey.trim();
    }

    public String getResVal() {
        return resVal;
    }

    public void setResVal(String resVal) {
        this.resVal = resVal == null ? null : resVal.trim();
    }
}