package com.har01d.lang.compiler.domain.variable;

import com.har01d.lang.compiler.domain.type.Type;

public class LocalValue extends LocalVariable {

    public LocalValue(String name, Type type, boolean initialized) {
        super(name, type, initialized);
    }

}
