package com.har01d.lang.compiler.domain.function;

import java.util.List;

import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class ConstructorCall implements Call {

    private final FunctionSignature signature;
    private final List<Argument> arguments;

    public ConstructorCall(FunctionSignature signature, List<Argument> arguments) {
        this.signature = signature;
        this.arguments = arguments;
    }

    @Override
    public List<Argument> getArguments() {
        return arguments;
    }

    public FunctionSignature getSignature() {
        return signature;
    }

    @Override
    public String getIdentifier() {
        return signature.getInternalName();
    }

    @Override
    public Type getType() {
        return BuiltInType.VOID;
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
