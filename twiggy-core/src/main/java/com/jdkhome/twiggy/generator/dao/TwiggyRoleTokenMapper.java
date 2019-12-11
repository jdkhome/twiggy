package com.jdkhome.twiggy.generator.dao;

import com.jdkhome.twiggy.generator.model.TwiggyRoleToken;
import com.jdkhome.twiggy.generator.model.TwiggyRoleTokenExample;
import com.jdkhome.twiggy.generator.model.TwiggyRoleTokenKey;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TwiggyRoleTokenMapper {
    long countByExample(TwiggyRoleTokenExample example);

    int deleteByExample(TwiggyRoleTokenExample example);

    int deleteByPrimaryKey(TwiggyRoleTokenKey key);

    int insert(TwiggyRoleToken record);

    int insertSelective(TwiggyRoleToken record);

    List<TwiggyRoleToken> selectByExample(TwiggyRoleTokenExample example);

    TwiggyRoleToken selectByPrimaryKey(TwiggyRoleTokenKey key);

    int updateByExampleSelective(@Param("record") TwiggyRoleToken record, @Param("example") TwiggyRoleTokenExample example);

    int updateByExample(@Param("record") TwiggyRoleToken record, @Param("example") TwiggyRoleTokenExample example);

    int updateByPrimaryKeySelective(TwiggyRoleToken record);

    int updateByPrimaryKey(TwiggyRoleToken record);
}