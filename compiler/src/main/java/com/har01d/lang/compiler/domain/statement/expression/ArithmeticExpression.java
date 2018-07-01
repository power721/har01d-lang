package com.har01d.lang.compiler.domain.statement.expression;

import com.har01d.lang.compiler.domain.type.BultInType;
import com.har01d.lang.compiler.domain.type.Type;

public abstract class ArithmeticExpression implements Expression {

    private final Expression leftExpression;
    private final Expression rightExpression;
    private final Type type;

    public ArithmeticExpression(Expression leftExpression,
        Expression rightExpression) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.type = getCommonType(leftExpression, rightExpression);
    }

    private static Type getCommonType(Expression leftExpression, Expression rightExpression) {
        if (rightExpression.getType() == BultInType.STRING) {
            return BultInType.STRING;
        }

        if (rightExpression.getType() == BultInType.DOUBLE) {
            return BultInType.DOUBLE;
        }

        return leftExpression.getType();
    }

    @Override
    public Type getType() {
        return type;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

}
