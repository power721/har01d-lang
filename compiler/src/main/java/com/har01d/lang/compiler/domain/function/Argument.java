package com.har01d.lang.compiler.domain.function;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class Argument implements Expression {

    private final String name;
    private final Expression expression;

    public Argument(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return expression.getType();
    }

    @Override
    public void accept(ExpressionGenerator generator) {
        expression.accept(generator);
    }

    @Override
    public void accept(StatementGenerator generator) {
        expression.accept(generator);
    }

}
