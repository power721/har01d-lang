package com.har01d.lang.compiler.domain.function;

import java.util.List;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class FunctionCall implements Call {

    private final Expression owner;
    private final FunctionSignature signature;
    private final List<Argument> arguments;
    private final Type type;

    public FunctionCall(Expression owner, FunctionSignature signature,
        List<Argument> arguments) {
        this.owner = owner;
        this.signature = signature;
        this.arguments = arguments;
        this.type = signature.getReturnType();
    }

    public Expression getOwner() {
        return owner;
    }

    public FunctionSignature getSignature() {
        return signature;
    }

    @Override
    public List<Argument> getArguments() {
        return arguments;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getIdentifier() {
        return signature.getInternalName();
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
