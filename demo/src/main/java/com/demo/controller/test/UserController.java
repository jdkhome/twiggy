package com.demo.controller.test;

import com.jdkhome.twiggy.core.authorize.TwiggyVirtualTokenService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * create by linkji.
 * create at 15:43 2019-12-11
 * 用户业务示范
 * 这里只作为权限使用的演示 实际编码中请依照各自规范执行
 */
@Slf4j
@RestController
@RequestMapping("/api/test/user")
public class UserController {

    private String success = "success";

    @Autowired
    TwiggyVirtualTokenService twiggyVirtualTokenService;

    /**
     * 用户登陆
     */
    @Data
    class UserSigninParams {
        String username;
        String password;
    }

    @RequestMapping("/signin")
    public String userSignin(@Valid UserSigninParams params) {
        // ... 校验账户密码
        if (params.username.equals("user123") && params.password.equals("pwd")) {
            // ... 从数据库或者其他地方 取出 该用户对应的 token
            String token = "c9a9f764197149b098345a9427009ec9";
            return twiggyVirtualTokenService.getVirtualToken(token, 30L, true);
        }
        return "账户或者密码错误";
    }

    /**
     * 用户登出
     */
    @RequestMapping("/signout")
    public String userSignout() {
        twiggyVirtualTokenService.delVirtualToken();
        return success;
    }


}
