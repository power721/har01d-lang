package com.har01d.lang.compiler.domain;

public class Value {
    private final int type;
    private final String value;

    public Value(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
