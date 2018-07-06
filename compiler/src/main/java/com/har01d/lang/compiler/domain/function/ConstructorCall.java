package com.har01d.lang.compiler.domain.function;

import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;
import java.util.List;

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

    public String getName() {
        return signature.getName();
    }

    @Override
    public String getIdentifier() {
        return signature.getInternalName();
    }

    @Override
    public Type getType() {
        return new ClassType(signature.getName());
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
