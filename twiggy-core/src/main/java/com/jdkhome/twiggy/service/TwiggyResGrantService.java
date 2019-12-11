package com.jdkhome.twiggy.service;

import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.generator.model.TwiggyResGrant;

import java.util.List;

/**
 * create by linkji.
 * create at 18:30 2019-12-09
 * 资源授权业务
 */
public interface TwiggyResGrantService {

    List<TwiggyResGrant> getAllResGrant(String token, String resKey);

    PageInfo<TwiggyResGrant> getResGrantWithPage(String token, String resKey, int page, int size);

    void addResGrant(String token, String resKey, String resVal);

    void delResGrant(String token, String resKey, String resVal);

}
