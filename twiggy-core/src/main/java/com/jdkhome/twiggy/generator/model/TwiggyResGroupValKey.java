package com.jdkhome.twiggy.generator.model;

public class TwiggyResGroupValKey {
    private String groupNo;

    private String resKey;

    private String resVal;

    public String getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(String groupNo) {
        this.groupNo = groupNo == null ? null : groupNo.trim();
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