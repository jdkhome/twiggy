package com.jdkhome.twiggy.service;

import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.generator.model.TwiggyRoleToken;

import java.util.List;

/**
 * create by linkji.
 * create at 18:34 2019-12-06
 * 角色业务接口
 */
public interface TwiggyRoleService {


    List<TwiggyRoleToken> getAllRoleToken(String roleKey, String token, Integer level);

    PageInfo<TwiggyRoleToken> getRoleTokenWithPage(String roleKey, String token, Integer level, int page, int size);

    void addRoleToken(String roleKey, String token, Integer level);

    TwiggyRoleToken getRoleToken(String roleKey, String token);

    void changeRoleToken(String roleKey, String token, Integer level);

    void delRoleToken(String roleKey, String token);

}
