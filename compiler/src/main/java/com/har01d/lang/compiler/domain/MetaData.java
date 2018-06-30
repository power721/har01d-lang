package com.har01d.lang.compiler.domain;

public class MetaData {

    private final String className;
    private final boolean isClassDeclaration;

    public MetaData(String className) {
        this.className = className;
        isClassDeclaration = false;
    }

    public MetaData(String className, boolean isClassDeclaration) {
        this.className = className;
        this.isClassDeclaration = isClassDeclaration;
    }

    public String getClassName() {
        return className;
    }

    public boolean isClassDeclaration() {
        return isClassDeclaration;
    }

}
