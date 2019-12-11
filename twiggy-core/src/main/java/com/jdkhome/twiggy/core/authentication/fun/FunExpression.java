package com.jdkhome.twiggy.core.authentication.fun;

import com.jdkhome.twiggy.core.authentication.CalcExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by linkji.
 * create at 15:14 2019-12-09
 * 功能权限表达式
 */
@Slf4j
public class FunExpression {

    public static boolean calcExp(String expression, Map<String, Integer> roleLevelMap) {
        return CalcExpression.calcExp(initExp(expression, roleLevelMap));
    }

    private static String initExp(String expression, Map<String, Integer> roleLevelMap) {

        expression = expression.replaceAll(" ", "");
        Pattern pattern = Pattern.compile("\\({1}([0-9]|[a-z]|[A-Z]|_)*(==|!=|>=|<=|>|<)(-?[0-9]+)\\){1}");
        Matcher matcher = pattern.matcher(expression);

        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            // 每一个符合正则的字符串
            String e = matcher.group();
            // 剔除前后括号
            String thisExp = e.substring(1, e.length() - 1);

            if (thisExp.indexOf("==") > 0) {
                String[] val = thisExp.split("==");
                matcher.appendReplacement(sb, roleLevelMap.get(val[0]) == null ? "false" : String.valueOf(roleLevelMap.get(val[0]) == Integer.parseInt(val[1])));
            } else if (thisExp.indexOf("!=") > 0) {
                String[] val = thisExp.split("!=");
                matcher.appendReplacement(sb, roleLevelMap.get(val[0]) == null ? "false" : String.valueOf(roleLevelMap.get(val[0]) != Integer.parseInt(val[1])));
            } else if (thisExp.indexOf(">=") > 0) {
                String[] val = thisExp.split(">=");
                matcher.appendReplacement(sb, roleLevelMap.get(val[0]) == null ? "false" : String.valueOf(roleLevelMap.get(val[0]) >= Integer.parseInt(val[1])));
            } else if (thisExp.indexOf(">") > 0) {
                String[] val = thisExp.split(">");
                matcher.appendReplacement(sb, roleLevelMap.get(val[0]) == null ? "false" : String.valueOf(roleLevelMap.get(val[0]) > Integer.parseInt(val[1])));
            } else if (thisExp.indexOf("<=") > 0) {
                String[] val = thisExp.split("<=");
                matcher.appendReplacement(sb, roleLevelMap.get(val[0]) == null ? "false" : String.valueOf(roleLevelMap.get(val[0]) <= Integer.parseInt(val[1])));
            } else if (thisExp.indexOf("<") > 0) {
                String[] val = thisExp.split("<");
                matcher.appendReplacement(sb, roleLevelMap.get(val[0]) == null ? "false" : String.valueOf(roleLevelMap.get(val[0]) < Integer.parseInt(val[1])));
            } else {
                // 都不是 则直接 认为是 true 或者 false
                matcher.appendReplacement(sb, String.valueOf(Boolean.parseBoolean(thisExp)));
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }


}
