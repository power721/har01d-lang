package com.har01d.lang.compiler.domain.statement.expression;

import com.har01d.lang.compiler.domain.CompareSign;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class RelationalExpression implements Expression {

    private final Expression leftExpression;
    private final Expression rightExpression;
    private final CompareSign compareSign;

    public RelationalExpression(Expression leftExpression,
        Expression rightExpression, CompareSign compareSign) {
        this.leftExpression = leftExpression;
        this.rightExpression = rightExpression;
        this.compareSign = compareSign;
    }

    public Expression getLeftExpression() {
        return leftExpression;
    }

    public Expression getRightExpression() {
        return rightExpression;
    }

    public CompareSign getCompareSign() {
        return compareSign;
    }

    @Override
    public Type getType() {
        return BuiltInType.BOOLEAN;
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
