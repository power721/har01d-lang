package com.har01d.lang.compiler.domain;

import com.har01d.lang.compiler.domain.type.Type;

public class LocalVariable {

    private final String name;
    private final Type type;

    public LocalVariable(String name, Type type) {
        this.type = type;
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

}
