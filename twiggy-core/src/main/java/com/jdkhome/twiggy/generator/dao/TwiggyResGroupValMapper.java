package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyResGroupVal;
import com.jdkhome.twiggy.generator.model.TwiggyResGroupValExample;
import com.jdkhome.twiggy.generator.model.TwiggyResGroupValKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyResGroupValMapper {
    long countByExample(TwiggyResGroupValExample example);

    int deleteByExample(TwiggyResGroupValExample example);

    int deleteByPrimaryKey(TwiggyResGroupValKey key);

    int insert(TwiggyResGroupVal record);

    int insertSelective(TwiggyResGroupVal record);

    List<TwiggyResGroupVal> selectByExample(TwiggyResGroupValExample example);

    TwiggyResGroupVal selectByPrimaryKey(TwiggyResGroupValKey key);

    int updateByExampleSelective(@Param("record") TwiggyResGroupVal record, @Param("example") TwiggyResGroupValExample example);

    int updateByExample(@Param("record") TwiggyResGroupVal record, @Param("example") TwiggyResGroupValExample example);

    int updateByPrimaryKeySelective(TwiggyResGroupVal record);

    int updateByPrimaryKey(TwiggyResGroupVal record);
}