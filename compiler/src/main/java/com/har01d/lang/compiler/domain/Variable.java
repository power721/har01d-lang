package com.har01d.lang.compiler.domain;

public class Variable {
    private final int id;
    private final int type;
    private final String value;

    public Variable(int id, int type, String value) {
        this.id = id;
        this.type = type;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getValue() {
        return value;
    }
}
