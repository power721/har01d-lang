package com.har01d.lang.compiler.domain.variable;

import com.har01d.lang.compiler.domain.type.Type;

public class LocalVariable {

    private final String name;
    private final Type type;
    private final boolean readonly;
    private boolean initialized;

    public LocalVariable(String name, Type type, boolean initialized, boolean readonly) {
        this.type = type;
        this.name = name;
        this.readonly = readonly;
        this.initialized = initialized;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

}
