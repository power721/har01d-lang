package com.har01d.lang.compiler.domain.statement.expression;

import com.har01d.lang.compiler.domain.type.BultInType;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class Power extends ArithmeticExpression {

    private Type type;

    public Power(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
        type = getCommonType(leftExpression, rightExpression);
    }

    private static Type getCommonType(Expression leftExpression, Expression rightExpression) {
        if (leftExpression.getType() == ClassType.BIGINTEGER && rightExpression.getType().equals(BultInType.INT)) {
            return ClassType.BIGINTEGER;
        }
        return BultInType.DOUBLE;
    }

    @Override
    public void accept(ExpressionGenerator generator) {
        generator.generate(this);
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

    @Override
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

}
