package com.har01d.lang.compiler.domain;

public class Literal {
    private final int type;
    private final String value;

    public Literal(int type, String value) {
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
