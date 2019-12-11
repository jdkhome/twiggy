package com.jdkhome.twiggy.common.enums;

import lombok.Getter;

/**
 * create by linkji.
 * create at 18:16 2019-12-06
 * token类型枚举
 */
@Getter
public enum TwiggyTokenTypeEnum {

    SUPER(0), // 主账号
    SUB(1), // 子账号
    ;

    int code;

    TwiggyTokenTypeEnum(int code) {
        this.code = code;
    }
}
