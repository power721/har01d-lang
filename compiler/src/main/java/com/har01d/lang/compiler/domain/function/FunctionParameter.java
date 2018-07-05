package com.har01d.lang.compiler.domain.function;

import java.util.Optional;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class FunctionParameter implements Expression {

    private final String name;
    private final Type type;
    private final Optional<Expression> defaultValue;

    public FunctionParameter(String name, Type type) {
        this.name = name;
        this.type = type;
        defaultValue = Optional.empty();
    }

    public FunctionParameter(String name, Type type, Optional<Expression> defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return name;
    }

    @Override
    public Type getType() {
        return type;
    }

    public Optional<Expression> getDefaultValue() {
        return defaultValue;
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
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FunctionParameter parameter = (FunctionParameter) o;

        return type.equals(parameter.type);
    }

    @Override
    public int hashCode() {
        return type.hashCode();
    }

}
