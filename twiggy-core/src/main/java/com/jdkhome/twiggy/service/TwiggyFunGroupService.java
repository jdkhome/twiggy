package com.jdkhome.twiggy.service;

import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.generator.model.TwiggyFunGroup;
import com.jdkhome.twiggy.generator.model.TwiggyFunGroupToken;
import com.jdkhome.twiggy.generator.model.TwiggyFunGroupVal;

import java.util.List;

/**
 * create by linkji.
 * create at 18:23 2019-12-06
 * 权限组业务接口
 */
public interface TwiggyFunGroupService {

    List<TwiggyFunGroup> getAllGroup(String groupNo, String createToken, String name);

    PageInfo<TwiggyFunGroup> getGroupWithPage(String groupNo, String createToken, String name, int page, int size);

    TwiggyFunGroup getGroup(String groupNo);

    String addGroup(String name, String createToken);

    void delGroup(String groupNo);


    List<TwiggyFunGroupVal> getAllGroupVal(String groupNo, String funKey);

    PageInfo<TwiggyFunGroupVal> getGroupValWithPage(String groupNo, String funKey, int page, int size);

    void addGroupVal(String groupNo, String funKey);

    void delGroupVal(String groupNo, String funKey);


    List<TwiggyFunGroupToken> getAllGroupToken(String groupNo, String token);

    PageInfo<TwiggyFunGroupToken> getGroupTokenWithPage(String groupNo, String token, int page, int size);

    void addGroupToken(String groupNo, String token);

    void delGroupToken(String groupNo, String token);

}
