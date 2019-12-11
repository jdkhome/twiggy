package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyResGrant;
import com.jdkhome.twiggy.generator.model.TwiggyResGrantExample;
import com.jdkhome.twiggy.generator.model.TwiggyResGrantKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyResGrantMapper {
    long countByExample(TwiggyResGrantExample example);

    int deleteByExample(TwiggyResGrantExample example);

    int deleteByPrimaryKey(TwiggyResGrantKey key);

    int insert(TwiggyResGrant record);

    int insertSelective(TwiggyResGrant record);

    List<TwiggyResGrant> selectByExample(TwiggyResGrantExample example);

    TwiggyResGrant selectByPrimaryKey(TwiggyResGrantKey key);

    int updateByExampleSelective(@Param("record") TwiggyResGrant record, @Param("example") TwiggyResGrantExample example);

    int updateByExample(@Param("record") TwiggyResGrant record, @Param("example") TwiggyResGrantExample example);

    int updateByPrimaryKeySelective(TwiggyResGrant record);

    int updateByPrimaryKey(TwiggyResGrant record);
}