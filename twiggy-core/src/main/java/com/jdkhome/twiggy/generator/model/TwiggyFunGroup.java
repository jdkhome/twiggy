package com.jdkhome.twiggy.generator.model;

import java.util.Date;

public class TwiggyFunGroup {
    private String groupNo;

    private String name;

    private String createToken;

    private Date createTime;

    private Date updateTime;

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo == null ? null : groupNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getCreateToken() {
        return createToken;
    }

    public void setCreateToken(String createToken) {
        this.createToken = createToken == null ? null : createToken.trim();
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