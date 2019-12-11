package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyFunGroup;
import com.jdkhome.twiggy.generator.model.TwiggyFunGroupExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyFunGroupMapper {
    long countByExample(TwiggyFunGroupExample example);

    int deleteByExample(TwiggyFunGroupExample example);

    int deleteByPrimaryKey(String groupNo);

    int insert(TwiggyFunGroup record);

    int insertSelective(TwiggyFunGroup record);

    List<TwiggyFunGroup> selectByExample(TwiggyFunGroupExample example);

    TwiggyFunGroup selectByPrimaryKey(String groupNo);

    int updateByExampleSelective(@Param("record") TwiggyFunGroup record, @Param("example") TwiggyFunGroupExample example);

    int updateByExample(@Param("record") TwiggyFunGroup record, @Param("example") TwiggyFunGroupExample example);

    int updateByPrimaryKeySelective(TwiggyFunGroup record);

    int updateByPrimaryKey(TwiggyFunGroup record);
}