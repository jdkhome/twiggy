package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyFunGroupVal;
import com.jdkhome.twiggy.generator.model.TwiggyFunGroupValExample;
import com.jdkhome.twiggy.generator.model.TwiggyFunGroupValKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyFunGroupValMapper {
    long countByExample(TwiggyFunGroupValExample example);

    int deleteByExample(TwiggyFunGroupValExample example);

    int deleteByPrimaryKey(TwiggyFunGroupValKey key);

    int insert(TwiggyFunGroupVal record);

    int insertSelective(TwiggyFunGroupVal record);

    List<TwiggyFunGroupVal> selectByExample(TwiggyFunGroupValExample example);

    TwiggyFunGroupVal selectByPrimaryKey(TwiggyFunGroupValKey key);

    int updateByExampleSelective(@Param("record") TwiggyFunGroupVal record, @Param("example") TwiggyFunGroupValExample example);

    int updateByExample(@Param("record") TwiggyFunGroupVal record, @Param("example") TwiggyFunGroupValExample example);

    int updateByPrimaryKeySelective(TwiggyFunGroupVal record);

    int updateByPrimaryKey(TwiggyFunGroupVal record);
}