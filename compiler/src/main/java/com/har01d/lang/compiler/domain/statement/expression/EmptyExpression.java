package com.har01d.lang.compiler.domain.statement.expression;

import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class EmptyExpression implements Expression {

    private final Type type;

    public EmptyExpression(Type type) {
        this.type = type;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void accept(ExpressionGenerator generator) {

    }

    @Override
    public void accept(StatementGenerator generator) {

    }

}
