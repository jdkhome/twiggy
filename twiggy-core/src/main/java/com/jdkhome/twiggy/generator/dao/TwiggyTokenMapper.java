package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyToken;
import com.jdkhome.twiggy.generator.model.TwiggyTokenExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyTokenMapper {
    long countByExample(TwiggyTokenExample example);

    int deleteByExample(TwiggyTokenExample example);

    int deleteByPrimaryKey(String token);

    int insert(TwiggyToken record);

    int insertSelective(TwiggyToken record);

    List<TwiggyToken> selectByExample(TwiggyTokenExample example);

    TwiggyToken selectByPrimaryKey(String token);

    int updateByExampleSelective(@Param("record") TwiggyToken record, @Param("example") TwiggyTokenExample example);

    int updateByExample(@Param("record") TwiggyToken record, @Param("example") TwiggyTokenExample example);

    int updateByPrimaryKeySelective(TwiggyToken record);

    int updateByPrimaryKey(TwiggyToken record);
}