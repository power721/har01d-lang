package com.har01d.lang.compiler.domain;

import com.har01d.lang.compiler.domain.statement.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class Literal implements Expression {

    private final Type type;
    private final String value;

    public Literal(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
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
