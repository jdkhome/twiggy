package com.jdkhome.twiggy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.generator.dao.TwiggyResGroupMapper;
import com.jdkhome.twiggy.generator.dao.TwiggyResGroupTokenMapper;
import com.jdkhome.twiggy.generator.dao.TwiggyResGroupValMapper;
import com.jdkhome.twiggy.generator.model.*;
import com.jdkhome.twiggy.service.TwiggyResGroupService;
import com.jdkhome.twiggy.utils.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by linkji.
 * create at 11:39 2019-12-10
 */
@Slf4j
@Service
public class TwiggyResGroupServiceImpl implements TwiggyResGroupService {

    @Autowired
    TwiggyResGroupMapper twiggyResGroupMapper;

    @Autowired
    TwiggyResGroupValMapper twiggyResGroupValMapper;

    @Autowired
    TwiggyResGroupTokenMapper twiggyResGroupTokenMapper;

    @Override
    public List<TwiggyResGroup> getAllGroup(String groupNo, String createToken, String name) {

        TwiggyResGroupExample example = new TwiggyResGroupExample();
        TwiggyResGroupExample.Criteria criteria = example.createCriteria();

        if (groupNo != null && groupNo.length() > 0) {
            criteria.andGroupNoEqualTo(groupNo);
        }

        if (createToken != null && createToken.length() > 0) {
            criteria.andCreateTokenEqualTo(createToken);
        }

        if (name != null && name.length() > 0) {
            criteria.andNameEqualTo(name);
        }

        return twiggyResGroupMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyResGroup> getGroupWithPage(String groupNo, String createToken, String name, int page, int size) {

        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllGroup(groupNo, createToken, name));
    }

    @Override
    public TwiggyResGroup getGroup(String groupNo) {
        return twiggyResGroupMapper.selectByPrimaryKey(groupNo);
    }

    @Override
    public String addGroup(String name, String createToken) {
        TwiggyResGroup resGroup = new TwiggyResGroup();
        resGroup.setGroupNo(UUIDGenerator.get());
        resGroup.setName(name);
        resGroup.setCreateToken(createToken);
        twiggyResGroupMapper.insertSelective(resGroup);
        return resGroup.getGroupNo();
    }

    @Override
    public void delGroup(String groupNo) {
        twiggyResGroupMapper.deleteByPrimaryKey(groupNo);
    }

    @Override
    public List<TwiggyResGroupVal> getAllGroupVal(String groupNo, String resKey) {

        TwiggyResGroupValExample example = new TwiggyResGroupValExample();
        TwiggyResGroupValExample.Criteria criteria = example.createCriteria();

        if (groupNo != null && groupNo.length() > 0) {
            criteria.andGroupNoEqualTo(groupNo);
        }

        if (resKey != null && resKey.length() > 0) {
            criteria.andResKeyEqualTo(resKey);
        }

        return twiggyResGroupValMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyResGroupVal> getGroupValWithPage(String groupNo, String resKey, int page, int size) {

        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllGroupVal(groupNo, resKey));
    }

    @Override
    public void addGroupVal(String groupNo, String resKey, String resVal) {

        TwiggyResGroupVal resGroupVal = new TwiggyResGroupVal();
        resGroupVal.setGroupNo(groupNo);
        resGroupVal.setResKey(resKey);
        resGroupVal.setResVal(resVal);

        twiggyResGroupValMapper.insertSelective(resGroupVal);
    }

    @Override
    public void delGroupVal(String groupNo, String resKey, String resVal) {

        TwiggyResGroupValKey resGroupValKey = new TwiggyResGroupValKey();
        resGroupValKey.setGroupNo(groupNo);
        resGroupValKey.setResKey(resKey);
        resGroupValKey.setResVal(resVal);

        twiggyResGroupValMapper.deleteByPrimaryKey(resGroupValKey);
    }

    @Override
    public List<TwiggyResGroupToken> getAllGroupToken(String groupNo, String token) {

        TwiggyResGroupTokenExample example = new TwiggyResGroupTokenExample();
        TwiggyResGroupTokenExample.Criteria criteria = example.createCriteria();

        if (groupNo != null && groupNo.length() > 0) {
            criteria.andGroupNoEqualTo(groupNo);
        }

        if (token != null && token.length() > 0) {
            criteria.andTokenEqualTo(token);
        }

        return twiggyResGroupTokenMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyResGroupToken> getGroupTokenWithPage(String groupNo, String token, int page, int size) {

        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllGroupToken(groupNo, token));
    }

    @Override
    public void addGroupToken(String groupNo, String token) {

        TwiggyResGroupToken resGroupToken = new TwiggyResGroupToken();
        resGroupToken.setGroupNo(groupNo);
        resGroupToken.setToken(token);

        twiggyResGroupTokenMapper.insertSelective(resGroupToken);
    }

    @Override
    public void delGroupToken(String groupNo, String token) {

        TwiggyResGroupTokenKey resGroupTokenKey = new TwiggyResGroupTokenKey();
        resGroupTokenKey.setGroupNo(groupNo);
        resGroupTokenKey.setToken(token);

        twiggyResGroupTokenMapper.deleteByPrimaryKey(resGroupTokenKey);

    }
}
