package com.har01d.lang.compiler.domain.statement.expression;

import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class Remainder extends ArithmeticExpression {

    public Remainder(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression);
    }

    @Override
    public void accept(ExpressionGenerator generator) {
        generator.generate(this);
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
