package com.har01d.lang.compiler.domain.function;

import com.har01d.lang.compiler.domain.type.FunctionType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.Reference;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class FunctionReference implements Reference {

    private final FunctionSignature functionSignature;
    private final FunctionType functionType;

    public FunctionReference(FunctionType functionType) {
        this.functionSignature = null;
        this.functionType = functionType;
    }

    public FunctionReference(FunctionSignature functionSignature) {
        this.functionSignature = functionSignature;
        this.functionType = functionSignature.getFunctionType();
    }

    public FunctionSignature getFunctionSignature() {
        return functionSignature;
    }

    public FunctionType getFunctionType() {
        return functionType;
    }

    @Override
    public String geName() {
        return functionSignature == null ? null : functionSignature.getName();
    }

    @Override
    public Type getType() {
        return functionType;
    }

    @Override
    public void accept(ExpressionGenerator generator) {

    }

    @Override
    public void accept(StatementGenerator generator) {

    }

}
