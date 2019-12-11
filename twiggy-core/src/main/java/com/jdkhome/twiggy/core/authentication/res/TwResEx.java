package com.jdkhome.twiggy.core.authentication.res;


import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * create by linkji.
 * create at 11:36 2019-12-05
 * 资源表达式
 * 支持嵌套
 * <p>
 * [(()||())&&()&&()&&()]
 */
@Getter
public class TwResEx {

    /**
     * 或 -> 这些表达式中只要有一个满足就返回 true 否则 false
     */
    List<TwResEx> or;

    /**
     * 且 -> 这些表达式必须全部满足
     */
    List<TwResEx> and;

    /**
     * 无子表达式的具体鉴权数据项
     */
    TwRes data;

    private TwResEx(List<TwResEx> or, List<TwResEx> and, TwRes data) {
        this.or = or;
        this.and = and;
        this.data = data;
    }

    public static TwResEx create(TwRes data) {
        assert data != null;
        return new TwResEx(null, null, data);
    }

    private static List<TwResEx> toList(Object... objs) {
        assert objs != null && objs.length != 0;
        List<TwResEx> expressions = new ArrayList<>(objs.length);
        for (int i = 0; i < objs.length; i++) {
            if (objs[i] instanceof TwResEx) {
                expressions.add((TwResEx) objs[i]);
            } else {
                expressions.add(new TwResEx(null, null, (TwRes) objs[i]));
            }
        }

        return expressions;
    }

    public static TwResEx createAnd(Object... objs) {
        return new TwResEx(null, toList(objs), null);
    }

    public static TwResEx createOr(Object... objs) {
        return new TwResEx(toList(objs), null, null);
    }

    public TwResEx and(Object... objs) {
        List<TwResEx> expressions = toList(objs);
        expressions.add(this);
        return new TwResEx(null, expressions, null);
    }

    public TwResEx or(Object... objs) {
        List<TwResEx> expressions = toList(objs);
        expressions.add(this);
        return new TwResEx(expressions, null, null);
    }


}
