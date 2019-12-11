package com.demo.pojo;

import com.jdkhome.twiggy.core.authentication.res.TwRes;
import lombok.Getter;

/**
 * create by linkji.
 * create at 11:56 2019-12-05
 */
@Getter
public enum DemoResEnum implements TwRes {

    USER_ID("userId", "用户Id"),
    ACCOUNT_NO("accountNo","账户号");

    String key;
    String name;

    private DemoResEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }
}
