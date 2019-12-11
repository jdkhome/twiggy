package com.demo.controller.debug;

import com.jdkhome.twiggy.core.authorize.TwiggyAuthorizeService;
import com.jdkhome.twiggy.service.TwiggyResGrantService;
import com.jdkhome.twiggy.service.TwiggyRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by linkji.
 * create at 15:52 2019-12-10
 * 授权控制器
 * 只可以给SuperToken 授权
 */
@RestController
@RequestMapping("/api/debug/grant")
public class GrantController {
    private String success = "success";

    @Autowired
    TwiggyResGrantService twiggyResGrantService;

    @Autowired
    TwiggyRoleService twiggyRoleService;

    @Autowired
    TwiggyAuthorizeService twiggyAuthorizeService;

    /**
     * 资源授权
     *
     * @return
     */
    @RequestMapping("/res")
    public String apiDebugGrantRes(String token, String resKey, String resVal) {
        twiggyResGrantService.addResGrant(token, resKey, resVal);
        twiggyAuthorizeService.refreshAuthDown(token);
        return success;
    }

    /**
     * 角色授权
     *
     * @return
     */
    @RequestMapping("/role")
    public String apiDebugGrantRole(String roleKey, String token, Integer level) {
        twiggyRoleService.addRoleToken(roleKey, token, level);
        twiggyAuthorizeService.refreshAuthDown(token);
        return success;
    }


}
