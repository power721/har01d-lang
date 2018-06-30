package com.har01d.lang.compiler.domain.function;

import com.har01d.lang.compiler.domain.type.Type;
import java.util.Collections;
import java.util.List;

public class FunctionSignature {

    private final String name;
    private final List<FunctionParameter> parameters;
    private final Type returnType;

    public FunctionSignature(String name, List<FunctionParameter> parameters, Type returnType) {
        this.name = name;
        this.parameters = parameters;
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public List<FunctionParameter> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    public Type getReturnType() {
        return returnType;
    }

    public boolean matches(String otherSignatureName, List<Argument> arguments) {
        if (!name.equals(otherSignatureName)) {
            return false;
        }
        if (parameters.size() != arguments.size()) {
            return false;
        }
        int i = 0;
        for (FunctionParameter parameter : parameters) {
            if (!parameter.getType().equals(arguments.get(i).getType())) {
                return false;
            }
            i++;
        }
        return true;
    }

}
