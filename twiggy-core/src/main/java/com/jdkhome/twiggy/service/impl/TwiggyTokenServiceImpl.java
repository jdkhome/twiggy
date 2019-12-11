package com.jdkhome.twiggy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.common.enums.TwiggyTokenTypeEnum;
import com.jdkhome.twiggy.generator.dao.TwiggyTokenMapper;
import com.jdkhome.twiggy.generator.model.TwiggyToken;
import com.jdkhome.twiggy.generator.model.TwiggyTokenExample;
import com.jdkhome.twiggy.service.TwiggyTokenService;
import com.jdkhome.twiggy.utils.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by linkji.
 * create at 11:11 2019-12-10
 */
@Slf4j
@Service
public class TwiggyTokenServiceImpl implements TwiggyTokenService {

    @Autowired
    TwiggyTokenMapper twiggyTokenMapper;

    @Override
    public List<TwiggyToken> getAllToken(String token, String superToken, TwiggyTokenTypeEnum type) {

        TwiggyTokenExample example = new TwiggyTokenExample();
        TwiggyTokenExample.Criteria criteria = example.createCriteria();

        if (token != null && token.length() > 0) {
            criteria.andTokenEqualTo(token);
        }

        if (superToken != null && superToken.length() > 0) {
            criteria.andSuperTokenEqualTo(superToken);
        }

        if (type != null) {
            criteria.andTypeEqualTo(type.getCode());
        }

        return twiggyTokenMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyToken> getTokenWithPage(String token, String superToken, TwiggyTokenTypeEnum type, int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllToken(token, superToken, type));
    }

    @Override
    public String createToken(TwiggyTokenTypeEnum type, String superToken) {

        TwiggyToken token = new TwiggyToken();
        token.setToken(UUIDGenerator.get());
        token.setType(type.getCode());
        if (type == TwiggyTokenTypeEnum.SUB) {
            token.setSuperToken(superToken);
        }

        twiggyTokenMapper.insertSelective(token);
        return token.getToken();
    }

    @Override
    public TwiggyToken getToken(String token) {

        return twiggyTokenMapper.selectByPrimaryKey(token);
    }

    @Override
    public void delToken(String token) {

        twiggyTokenMapper.deleteByPrimaryKey(token);
    }

    @Override
    public void changeToken(String token, String superToken) {

        TwiggyToken thisToken = new TwiggyToken();
        thisToken.setToken(token);
        thisToken.setSuperToken(superToken);

        twiggyTokenMapper.updateByPrimaryKeySelective(thisToken);

    }
}
