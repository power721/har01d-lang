package com.har01d.lang.compiler.domain.type;

import java.util.List;

public class FunctionType implements Type {

    private final List<Type> parameters;
    private final Type returnType;

    public FunctionType(List<Type> parameters, Type returnType) {
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public List<Type> getParameters() {
        return parameters;
    }

    public Type getReturnType() {
        return returnType;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Class<?> getTypeClass() {
        return null;
    }

    @Override
    public String getDescriptor() {
        return null;
    }

    @Override
    public String getInternalName() {
        return null;
    }

    @Override
    public int getLoadVariableOpcode() {
        return 0;
    }

    @Override
    public int getStoreVariableOpcode() {
        return 0;
    }

    @Override
    public int getReturnOpcode() {
        return 0;
    }

    @Override
    public int getAddOpcode() {
        return 0;
    }

    @Override
    public int getSubtractOpcode() {
        return 0;
    }

    @Override
    public int getMultiplyOpcode() {
        return 0;
    }

    @Override
    public int getDivideOpcode() {
        return 0;
    }

    @Override
    public int getRemainderOpcode() {
        return 0;
    }
}
