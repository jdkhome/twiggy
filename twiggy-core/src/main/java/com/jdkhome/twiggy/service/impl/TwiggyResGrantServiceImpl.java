package com.jdkhome.twiggy.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.twiggy.generator.dao.TwiggyResGrantMapper;
import com.jdkhome.twiggy.generator.model.TwiggyResGrant;
import com.jdkhome.twiggy.generator.model.TwiggyResGrantExample;
import com.jdkhome.twiggy.generator.model.TwiggyResGrantKey;
import com.jdkhome.twiggy.service.TwiggyResGrantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create by linkji.
 * create at 12:04 2019-12-10
 */
@Slf4j
@Service
public class TwiggyResGrantServiceImpl implements TwiggyResGrantService {

    @Autowired
    TwiggyResGrantMapper twiggyResGrantMapper;

    @Override
    public List<TwiggyResGrant> getAllResGrant(String token, String resKey) {

        TwiggyResGrantExample example = new TwiggyResGrantExample();
        TwiggyResGrantExample.Criteria criteria = example.createCriteria();

        if (token != null && token.length() > 0) {
            criteria.andTokenEqualTo(token);
        }

        if (resKey != null && resKey.length() > 0) {
            criteria.andResKeyEqualTo(resKey);
        }

        return twiggyResGrantMapper.selectByExample(example);
    }

    @Override
    public PageInfo<TwiggyResGrant> getResGrantWithPage(String token, String resKey, int page, int size) {

        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllResGrant(token, resKey));
    }

    @Override
    public void addResGrant(String token, String resKey, String resVal) {

        TwiggyResGrant resGrant = new TwiggyResGrant();
        resGrant.setToken(token);
        resGrant.setResKey(resKey);
        resGrant.setResVal(resVal);

        twiggyResGrantMapper.insertSelective(resGrant);
    }

    @Override
    public void delResGrant(String token, String resKey, String resVal) {

        TwiggyResGrantKey resGrantKey = new TwiggyResGrant();
        resGrantKey.setToken(token);
        resGrantKey.setResKey(resKey);
        resGrantKey.setResVal(resVal);

        twiggyResGrantMapper.deleteByPrimaryKey(resGrantKey);
    }
}
