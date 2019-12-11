package com.jdkhome.twiggy.service;

import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.generator.model.TwiggyResGroup;
import com.jdkhome.twiggy.generator.model.TwiggyResGroupToken;
import com.jdkhome.twiggy.generator.model.TwiggyResGroupVal;

import java.util.List;

/**
 * create by linkji.
 * create at 18:40 2019-12-06
 * 资源组接口
 */
public interface TwiggyResGroupService {

    List<TwiggyResGroup> getAllGroup(String groupNo, String createToken, String name);

    PageInfo<TwiggyResGroup> getGroupWithPage(String groupNo, String createToken, String name, int page, int size);

    TwiggyResGroup getGroup(String groupNo);

    String addGroup(String name, String createToken);

    void delGroup(String groupNo);


    List<TwiggyResGroupVal> getAllGroupVal(String groupNo, String resKey);

    PageInfo<TwiggyResGroupVal> getGroupValWithPage(String groupNo, String resKey, int page, int size);

    void addGroupVal(String groupNo, String resKey, String resVal);

    void delGroupVal(String groupNo, String resKey, String resVal);


    List<TwiggyResGroupToken> getAllGroupToken(String groupNo, String token);

    PageInfo<TwiggyResGroupToken> getGroupTokenWithPage(String groupNo, String token, int page, int size);

    void addGroupToken(String groupNo, String token);

    void delGroupToken(String groupNo, String token);

}
