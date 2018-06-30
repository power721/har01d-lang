package com.har01d.lang.compiler.domain;

import com.har01d.lang.compiler.domain.function.Function;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassDeclaration {

    private String name;
    private Collection<Function> methods;

    public ClassDeclaration(String name, Collection<Function> methods) {
        this.name = name;
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public List<Function> getMethods() {
        return new ArrayList<>(methods);
    }

}
