package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyResGroup;
import com.jdkhome.twiggy.generator.model.TwiggyResGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyResGroupMapper {
    long countByExample(TwiggyResGroupExample example);

    int deleteByExample(TwiggyResGroupExample example);

    int deleteByPrimaryKey(String groupNo);

    int insert(TwiggyResGroup record);

    int insertSelective(TwiggyResGroup record);

    List<TwiggyResGroup> selectByExample(TwiggyResGroupExample example);

    TwiggyResGroup selectByPrimaryKey(String groupNo);

    int updateByExampleSelective(@Param("record") TwiggyResGroup record, @Param("example") TwiggyResGroupExample example);

    int updateByExample(@Param("record") TwiggyResGroup record, @Param("example") TwiggyResGroupExample example);

    int updateByPrimaryKeySelective(TwiggyResGroup record);

    int updateByPrimaryKey(TwiggyResGroup record);
}