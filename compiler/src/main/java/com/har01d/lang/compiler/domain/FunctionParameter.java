package com.har01d.lang.compiler.domain;

import com.har01d.lang.compiler.domain.statement.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class FunctionParameter implements Expression {

    private final String name;
    private final Type type;

    public FunctionParameter(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
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
