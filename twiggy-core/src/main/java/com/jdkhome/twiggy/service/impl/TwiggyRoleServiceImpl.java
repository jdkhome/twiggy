package com.jdkhome.twiggy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.generator.dao.TwiggyRoleTokenMapper;
import com.jdkhome.twiggy.generator.model.TwiggyRoleToken;
import com.jdkhome.twiggy.generator.model.TwiggyRoleTokenExample;
import com.jdkhome.twiggy.generator.model.TwiggyRoleTokenKey;
import com.jdkhome.twiggy.service.TwiggyRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by linkji.
 * create at 11:29 2019-12-10
 */
@Slf4j
@Service
public class TwiggyRoleServiceImpl implements TwiggyRoleService {

    @Autowired
    TwiggyRoleTokenMapper twiggyRoleTokenMapper;

    @Override
    public List<TwiggyRoleToken> getAllRoleToken(String roleKey, String token, Integer level) {

        TwiggyRoleTokenExample example = new TwiggyRoleTokenExample();
        TwiggyRoleTokenExample.Criteria criteria = example.createCriteria();

        if (roleKey != null && roleKey.length() > 0) {
            criteria.andRoleKeyEqualTo(roleKey);
        }

        if (token != null && token.length() > 0) {
            criteria.andTokenEqualTo(token);
        }

        if (level != null) {
            criteria.andLevelEqualTo(level);
        }

        return twiggyRoleTokenMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyRoleToken> getRoleTokenWithPage(String roleKey, String token, Integer level, int page, int size) {

        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllRoleToken(roleKey, token, level));
    }

    @Override
    public void addRoleToken(String roleKey, String token, Integer level) {
        TwiggyRoleToken roleToken = new TwiggyRoleToken();
        roleToken.setRoleKey(roleKey);
        roleToken.setToken(token);
        roleToken.setLevel(level);
        twiggyRoleTokenMapper.insertSelective(roleToken);
    }

    @Override
    public TwiggyRoleToken getRoleToken(String roleKey, String token) {
        TwiggyRoleTokenKey roleTokenKey = new TwiggyRoleTokenKey();
        roleTokenKey.setRoleKey(roleKey);
        roleTokenKey.setToken(token);
        return twiggyRoleTokenMapper.selectByPrimaryKey(roleTokenKey);
    }

    @Override
    public void changeRoleToken(String roleKey, String token, Integer level) {
        TwiggyRoleToken roleToken = new TwiggyRoleToken();
        roleToken.setRoleKey(roleKey);
        roleToken.setToken(token);
        roleToken.setLevel(level);
        twiggyRoleTokenMapper.updateByPrimaryKeySelective(roleToken);
    }

    @Override
    public void delRoleToken(String roleKey, String token) {
        TwiggyRoleTokenKey roleTokenKey = new TwiggyRoleTokenKey();
        roleTokenKey.setRoleKey(roleKey);
        roleTokenKey.setToken(token);
        twiggyRoleTokenMapper.deleteByPrimaryKey(roleTokenKey);
    }
}
