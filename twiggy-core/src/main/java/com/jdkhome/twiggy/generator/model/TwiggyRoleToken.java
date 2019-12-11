package com.jdkhome.twiggy.generator.model;

import java.util.Date;

public class TwiggyRoleToken extends TwiggyRoleTokenKey {
    private Integer level;

    private Date createTime;

    private Date updateTime;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
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