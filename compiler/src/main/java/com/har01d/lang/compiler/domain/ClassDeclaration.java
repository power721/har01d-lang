package com.har01d.lang.compiler.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.har01d.lang.compiler.domain.function.Function;

public class ClassDeclaration {

    private final String name;
    private final Collection<Function> Constructors;
    private final Collection<Function> methods;

    public ClassDeclaration(String name, Collection<Function> constructors, Collection<Function> methods) {
        this.name = name;
        Constructors = constructors;
        this.methods = methods;
    }

    public String getName() {
        return name;
    }

    public Collection<Function> getConstructors() {
        return Constructors;
    }

    public List<Function> getMethods() {
        return new ArrayList<>(methods);
    }

}
