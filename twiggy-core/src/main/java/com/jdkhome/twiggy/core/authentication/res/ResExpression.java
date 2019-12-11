package com.jdkhome.twiggy.core.authentication.res;

import com.jdkhome.twiggy.core.authentication.CalcExpression;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * create by linkji.
 * create at 18:12 2019-12-10
 * 资源权限表达式
 */
@Slf4j
public class ResExpression {

    public static boolean calcExp(String expression, Map<String, String> resMap, Map<String, Set<String>> resAuthMap) {
        return CalcExpression.calcExp(initExp(expression, resMap, resAuthMap));
    }

    private static String initExp(String expression, Map<String, String> resMap, Map<String, Set<String>> resAuthMap) {
        expression = expression.replaceAll(" ", "");
        Pattern pattern = Pattern.compile("([0-9]|[a-z]|[A-Z]|_)+");
        Matcher matcher = pattern.matcher(expression);

        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            // 每一个符合正则的字符串
            String thisExp = matcher.group();

            // 待校验的资源Map 或者 已有资源的Map 中未命中 则直接为false
            if (resMap == null || resAuthMap == null || !resMap.containsKey(thisExp) || !resAuthMap.containsKey(thisExp)) {
                matcher.appendReplacement(sb, "false");
            } else {
                matcher.appendReplacement(sb, String.valueOf(resAuthMap.get(thisExp).contains(resMap.get(thisExp))));
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
