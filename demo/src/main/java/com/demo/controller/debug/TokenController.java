package com.demo.controller.debug;

import com.jdkhome.twiggy.common.enums.TwiggyTokenTypeEnum;
import com.jdkhome.twiggy.core.authorize.TwiggyVirtualTokenService;
import com.jdkhome.twiggy.service.TwiggyTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * create by linkji.
 * create at 15:49 2019-12-10
 * token相关控制器
 */
@RestController
@RequestMapping("/api/debug/token")
public class TokenController {

    private String success = "success";

    @Autowired
    TwiggyTokenService twiggyTokenService;

    @Autowired
    TwiggyVirtualTokenService twiggyVirtualTokenService;

    /**
     * 获取一个虚拟授权凭证
     *
     * @param token 永久凭证
     * @return
     */
    @RequestMapping("/get/virtual_token")
    public String apiGetVirtualToken(String token) {
        return twiggyVirtualTokenService.getVirtualToken(token, 30L, true);
    }

    /**
     * 创建一个主账户
     *
     * @return
     */
    @RequestMapping("/create/super_token")
    public String apiCreateSuperToken() {
        return twiggyTokenService.createToken(TwiggyTokenTypeEnum.SUPER, null);
    }

    /**
     * 创建一个子账户
     *
     * @return
     */
    @RequestMapping("/create/sub_token")
    public String apiCreateSubToken(String superToken) {
        return twiggyTokenService.createToken(TwiggyTokenTypeEnum.SUB, superToken);
    }
}
