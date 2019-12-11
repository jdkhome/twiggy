package com.demo.pojo;

import lombok.Data;

/**
 * create by linkji.
 * create at 11:54 2019-12-05
 * 随便举例一个Order实体
 */
@Data
public class Order {

    Integer userId;

    String accountNo;

    String value;

    public Order() {
    }

    public Order(Integer userId, String accountNo, String value) {
        this.userId = userId;
        this.accountNo = accountNo;
        this.value = value;
    }
}
