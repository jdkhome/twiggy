package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyFunGroupToken;
import com.jdkhome.twiggy.generator.model.TwiggyFunGroupTokenExample;
import com.jdkhome.twiggy.generator.model.TwiggyFunGroupTokenKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyFunGroupTokenMapper {
    long countByExample(TwiggyFunGroupTokenExample example);

    int deleteByExample(TwiggyFunGroupTokenExample example);

    int deleteByPrimaryKey(TwiggyFunGroupTokenKey key);

    int insert(TwiggyFunGroupToken record);

    int insertSelective(TwiggyFunGroupToken record);

    List<TwiggyFunGroupToken> selectByExample(TwiggyFunGroupTokenExample example);

    TwiggyFunGroupToken selectByPrimaryKey(TwiggyFunGroupTokenKey key);

    int updateByExampleSelective(@Param("record") TwiggyFunGroupToken record, @Param("example") TwiggyFunGroupTokenExample example);

    int updateByExample(@Param("record") TwiggyFunGroupToken record, @Param("example") TwiggyFunGroupTokenExample example);

    int updateByPrimaryKeySelective(TwiggyFunGroupToken record);

    int updateByPrimaryKey(TwiggyFunGroupToken record);
}