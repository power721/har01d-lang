package com.har01d.lang.compiler.domain.type;

public interface Type {

    String getName();

    Class<?> getTypeClass();

    String getDescriptor();

    String getInternalName();

    int getLoadVariableOpcode();

    int getStoreVariableOpcode();

    int getReturnOpcode();

    int getAddOpcode();

    int getSubtractOpcode();

    int getMultiplyOpcode();

    int getDivideOpcode();

    int getRemainderOpcode();

}
