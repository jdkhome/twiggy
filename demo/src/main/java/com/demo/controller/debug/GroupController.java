package com.demo.controller.debug;

import com.jdkhome.twiggy.core.authorize.TwiggyAuthorizeService;
import com.jdkhome.twiggy.service.TwiggyFunGroupService;
import com.jdkhome.twiggy.service.TwiggyResGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by linkji.
 * create at 15:56 2019-12-10
 */
@RestController
@RequestMapping("/api/debug/group")
public class GroupController {

    private String success = "success";

    @Autowired
    TwiggyFunGroupService twiggyFunGroupService;

    @Autowired
    TwiggyResGroupService twiggyResGroupService;

    @Autowired
    TwiggyAuthorizeService twiggyAuthorizeService;

    /**
     * 创建权限组
     *
     * @return
     */
    @RequestMapping("/fun/create")
    public String apiDebugGroupFunCreate(String name, String createToken) {
        return twiggyFunGroupService.addGroup(name, createToken);
    }

    /**
     * 加入权限组
     *
     * @return
     */
    @RequestMapping("/fun/join_in")
    public String apiDebugGroupFunJoinIn(String groupNo, String token) {
        twiggyFunGroupService.addGroupToken(groupNo, token);
        twiggyAuthorizeService.refreshAuthDown(token);
        return success;
    }

    /**
     * 权限组授权
     *
     * @return
     */
    @RequestMapping("/fun/grant")
    public String apiDebugGroupFunGrant(String groupNo, String funKey) {
        twiggyFunGroupService.addGroupVal(groupNo, funKey);
        twiggyAuthorizeService.refreshAuthDown(twiggyFunGroupService.getGroup(groupNo).getCreateToken());
        return success;
    }

    /**
     * 创建资源组
     *
     * @return
     */
    @RequestMapping("/res/create")
    public String apiDebugGroupResCreate(String name, String createToken) {
        return twiggyResGroupService.addGroup(name, createToken);
    }

    /**
     * 加入资源组
     *
     * @return
     */
    @RequestMapping("/res/join_in")
    public String apiDebugGroupResJoinIn(String groupNo, String token) {
        twiggyResGroupService.addGroupToken(groupNo, token);
        twiggyAuthorizeService.refreshAuthDown(token);
        return success;
    }

    /**
     * 资源组授权
     *
     * @return
     */
    @RequestMapping("/res/grant")
    public String apiDebugGroupResGrant(String groupNo, String resKey, String resVal) {
        twiggyResGroupService.addGroupVal(groupNo, resKey, resVal);
        twiggyAuthorizeService.refreshAuthDown(twiggyResGroupService.getGroup(groupNo).getCreateToken());
        return success;
    }

}
