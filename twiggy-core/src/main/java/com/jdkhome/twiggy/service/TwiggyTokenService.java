package com.jdkhome.twiggy.service;

import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.common.enums.TwiggyTokenTypeEnum;
import com.jdkhome.twiggy.generator.model.TwiggyToken;

import java.util.List;

/**
 * create by linkji.
 * create at 18:43 2019-12-06
 * token接口
 */
public interface TwiggyTokenService {

    List<TwiggyToken> getAllToken(String token, String superToken, TwiggyTokenTypeEnum type);

    PageInfo<TwiggyToken> getTokenWithPage(String token, String superToken, TwiggyTokenTypeEnum type, int page, int size);

    String createToken(TwiggyTokenTypeEnum type, String superToken);

    TwiggyToken getToken(String token);

    void delToken(String token);

    void changeToken(String token, String superToken);

}
