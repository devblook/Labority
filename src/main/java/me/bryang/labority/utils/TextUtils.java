package me.bryang.labority.utils;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class TextUtils {

    public static int calculateNumber(String format, int level) {
        Expression expression = new ExpressionBuilder(format)
                .variables("%j")
                .build()
                .setVariable("%level", level);

        return (int) expression.evaluate();
    }
}
