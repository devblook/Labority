package me.bryang.workity.utils;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class TextUtils {

    public static int calculateNumber(String format, int level) {

        String mathFormat = format.replace("%level%", String.valueOf(level));

        Expression expression = new ExpressionBuilder(mathFormat).build();
        return (int) expression.evaluate();
    }
}
