package com.jdkhome.twiggy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.generator.dao.TwiggyFunGroupMapper;
import com.jdkhome.twiggy.generator.dao.TwiggyFunGroupTokenMapper;
import com.jdkhome.twiggy.generator.dao.TwiggyFunGroupValMapper;
import com.jdkhome.twiggy.generator.model.*;
import com.jdkhome.twiggy.service.TwiggyFunGroupService;
import com.jdkhome.twiggy.utils.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by linkji.
 * create at 12:14 2019-12-10
 */
@Slf4j
@Service
public class TwiggyFunGroupServiceImpl implements TwiggyFunGroupService {

    @Autowired
    TwiggyFunGroupMapper twiggyFunGroupMapper;

    @Autowired
    TwiggyFunGroupValMapper twiggyFunGroupValMapper;

    @Autowired
    TwiggyFunGroupTokenMapper twiggyFunGroupTokenMapper;

    @Override
    public List<TwiggyFunGroup> getAllGroup(String groupNo, String createToken, String name) {

        TwiggyFunGroupExample example = new TwiggyFunGroupExample();
        TwiggyFunGroupExample.Criteria criteria = example.createCriteria();

        if (groupNo != null && groupNo.length() > 0) {
            criteria.andGroupNoEqualTo(groupNo);
        }

        if (createToken != null && createToken.length() > 0) {
            criteria.andCreateTokenEqualTo(createToken);
        }

        if (name != null && name.length() > 0) {
            criteria.andNameEqualTo(name);
        }

        return twiggyFunGroupMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyFunGroup> getGroupWithPage(String groupNo, String createToken, String name, int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllGroup(groupNo, createToken, name));
    }

    @Override
    public TwiggyFunGroup getGroup(String groupNo) {
        return twiggyFunGroupMapper.selectByPrimaryKey(groupNo);
    }

    @Override
    public String addGroup(String name, String createToken) {

        TwiggyFunGroup funGroup = new TwiggyFunGroup();
        funGroup.setGroupNo(UUIDGenerator.get());
        funGroup.setName(name);
        funGroup.setCreateToken(createToken);

        twiggyFunGroupMapper.insertSelective(funGroup);

        return funGroup.getGroupNo();
    }

    @Override
    public void delGroup(String groupNo) {
        twiggyFunGroupMapper.deleteByPrimaryKey(groupNo);
    }

    @Override
    public List<TwiggyFunGroupVal> getAllGroupVal(String groupNo, String funKey) {

        TwiggyFunGroupValExample example = new TwiggyFunGroupValExample();
        TwiggyFunGroupValExample.Criteria criteria = example.createCriteria();

        if (groupNo != null && groupNo.length() > 0) {
            criteria.andGroupNoEqualTo(groupNo);
        }

        if (funKey != null && funKey.length() > 0) {
            criteria.andFunKeyEqualTo(funKey);
        }

        return twiggyFunGroupValMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyFunGroupVal> getGroupValWithPage(String groupNo, String funKey, int page, int size) {
        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllGroupVal(groupNo, funKey));
    }

    @Override
    public void addGroupVal(String groupNo, String funKey) {
        TwiggyFunGroupVal funGroupVal = new TwiggyFunGroupVal();
        funGroupVal.setGroupNo(groupNo);
        funGroupVal.setFunKey(funKey);

        twiggyFunGroupValMapper.insertSelective(funGroupVal);
    }

    @Override
    public void delGroupVal(String groupNo, String funKey) {

        TwiggyFunGroupValKey funGroupValKey = new TwiggyFunGroupValKey();
        funGroupValKey.setGroupNo(groupNo);
        funGroupValKey.setFunKey(funKey);

        twiggyFunGroupValMapper.deleteByPrimaryKey(funGroupValKey);
    }

    @Override
    public List<TwiggyFunGroupToken> getAllGroupToken(String groupNo, String token) {

        TwiggyFunGroupTokenExample example = new TwiggyFunGroupTokenExample();
        TwiggyFunGroupTokenExample.Criteria criteria = example.createCriteria();

        if (groupNo != null && groupNo.length() > 0) {
            criteria.andGroupNoEqualTo(groupNo);
        }

        if (token != null && token.length() > 0) {
            criteria.andTokenEqualTo(token);
        }

        return twiggyFunGroupTokenMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyFunGroupToken> getGroupTokenWithPage(String groupNo, String token, int page, int size) {

        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllGroupToken(groupNo, token));
    }

    @Override
    public void addGroupToken(String groupNo, String token) {
        TwiggyFunGroupToken funGroupToken = new TwiggyFunGroupToken();
        funGroupToken.setGroupNo(groupNo);
        funGroupToken.setToken(token);

        twiggyFunGroupTokenMapper.insertSelective(funGroupToken);
    }

    @Override
    public void delGroupToken(String groupNo, String token) {

        TwiggyFunGroupTokenKey funGroupTokenKey = new TwiggyFunGroupTokenKey();
        funGroupTokenKey.setGroupNo(groupNo);
        funGroupTokenKey.setToken(token);

        twiggyFunGroupTokenMapper.deleteByPrimaryKey(funGroupTokenKey);
    }
}
