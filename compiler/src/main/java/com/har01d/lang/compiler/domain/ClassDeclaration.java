package com.har01d.lang.compiler.domain;

import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClassDeclaration {

    private final String name;
    private final Type type;
    private final Collection<Function> Constructors;
    private final Collection<Function> methods;

    public ClassDeclaration(String name, Collection<Function> constructors, Collection<Function> methods) {
        this.name = name;
        this.methods = methods;
        type = new ClassType(name);
        Constructors = constructors;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Collection<Function> getConstructors() {
        return Constructors;
    }

    public List<Function> getMethods() {
        return new ArrayList<>(methods);
    }

}
