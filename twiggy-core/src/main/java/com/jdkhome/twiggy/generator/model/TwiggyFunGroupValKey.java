package com.jdkhome.twiggy.generator.model;

public class TwiggyFunGroupValKey {
    private String groupNo;

    private String funKey;

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo == null ? null : groupNo.trim();
    }

    public String getFunKey() {
        return funKey;
    }

    public void setFunKey(String funKey) {
        this.funKey = funKey == null ? null : funKey.trim();
    }
}