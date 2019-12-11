package com.jdkhome.twiggy.generator.model;

import java.util.Date;

public class TwiggyToken {
    private String token;

    private String superToken;

    private Integer type;

    private Date createTime;

    private Date updateTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getSuperToken() {
        return superToken;
    }

    public void setSuperToken(String superToken) {
        this.superToken = superToken == null ? null : superToken.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}