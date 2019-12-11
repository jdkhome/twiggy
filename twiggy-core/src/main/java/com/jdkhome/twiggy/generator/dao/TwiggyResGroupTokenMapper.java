package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyResGroupToken;
import com.jdkhome.twiggy.generator.model.TwiggyResGroupTokenExample;
import com.jdkhome.twiggy.generator.model.TwiggyResGroupTokenKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyResGroupTokenMapper {
    long countByExample(TwiggyResGroupTokenExample example);

    int deleteByExample(TwiggyResGroupTokenExample example);

    int deleteByPrimaryKey(TwiggyResGroupTokenKey key);

    int insert(TwiggyResGroupToken record);

    int insertSelective(TwiggyResGroupToken record);

    List<TwiggyResGroupToken> selectByExample(TwiggyResGroupTokenExample example);

    TwiggyResGroupToken selectByPrimaryKey(TwiggyResGroupTokenKey key);

    int updateByExampleSelective(@Param("record") TwiggyResGroupToken record, @Param("example") TwiggyResGroupTokenExample example);

    int updateByExample(@Param("record") TwiggyResGroupToken record, @Param("example") TwiggyResGroupTokenExample example);

    int updateByPrimaryKeySelective(TwiggyResGroupToken record);

    int updateByPrimaryKey(TwiggyResGroupToken record);
}