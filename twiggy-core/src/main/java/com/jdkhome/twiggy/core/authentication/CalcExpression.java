package com.jdkhome.twiggy.core.authentication;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * create by linkji.
 * create at 18:28 2019-12-10
 */
@Slf4j
public class CalcExpression {

    public static boolean calcExp(String expression) {

        if (expression.equals("true")) {
            return true;
        } else if (expression.equals("false")) {
            return false;
        } else {
            List<String> splitExp = toExpOpeStr(expression);
//            log.info("{} => {}", expression, splitExp);
            if (splitExp.get(1).equals("&&")) {
                return calcExp(splitExp.get(0)) && calcExp(splitExp.get(2));
            } else {
                return calcExp(splitExp.get(0)) || calcExp(splitExp.get(2));
            }
        }
    }

    private static List<String> toExpOpeStr(String boolExpression) {

        List<String> result = new ArrayList<>();

        // 获取第一个EXP
        if (boolExpression.startsWith("(")) {
            StringBuilder sb = new StringBuilder();
            int flag = 0;
            for (int i = 0; i < boolExpression.length(); i++) {
                switch (boolExpression.charAt(i)) {
                    case '(':
                        if (flag > 0) {
                            sb.append(boolExpression.charAt(i));
                        }
                        flag++;
                        break;
                    case ')':
                        flag--;
                        if (flag > 0) {
                            sb.append(boolExpression.charAt(i));
                        }
                        break;
                    default:
                        sb.append(boolExpression.charAt(i));
                        break;
                }
                if (flag == 0) {
                    if (i == boolExpression.length() - 1) {
                        // 这种情况是前后括号 直接去除
                        return toExpOpeStr(sb.toString());
                    }
                    result.add(sb.toString());
                    result.add(boolExpression.substring(i + 1, i + 3));
                    result.add(boolExpression.substring(i + 3));
                    return result;
                }
            }
            return null;
        } else {
            if (boolExpression.startsWith("true")) {
                result.add("true");
                result.add(boolExpression.substring(4, 6));
                result.add(boolExpression.substring(6));
                return result;
            } else {
                result.add("false");
                result.add(boolExpression.substring(5, 7));
                result.add(boolExpression.substring(7));
                return result;
            }
        }

    }
}
