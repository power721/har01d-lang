package com.har01d.lang.compiler.domain.function;

import java.util.Collections;
import java.util.List;

import com.har01d.lang.compiler.domain.type.Type;

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

        if (parameters.size() < arguments.size()) {
            return false;
        }

        int i = 0;
        for (; i < arguments.size(); ++i) {
            if (!arguments.get(i).getType().equals(parameters.get(i).getType())) {
                return false;
            }
        }

        for (; i < parameters.size(); ++i) {
            if (!parameters.get(i).getDefaultValue().isPresent()) {
                return false;
            }
        }

        return true;
    }

}
