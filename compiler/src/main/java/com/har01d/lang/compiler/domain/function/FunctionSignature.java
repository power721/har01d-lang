package com.har01d.lang.compiler.domain.function;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

        if (isNamedArguments(arguments)) {
            return matchesNamedArguments(arguments);
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

    public List<Argument> getArguments(List<Argument> arguments) {
        if (isNamedArguments(arguments)) {
            List<Argument> result = new ArrayList<>();
            for (FunctionParameter parameter : parameters) {
                Optional<Argument> argument = arguments.stream().filter(e -> e.getName().equals(parameter.getName()))
                                                .findFirst();
                if (argument.isPresent()) {
                    result.add(argument.get());
                } else {
                    result.add(new Argument(parameter.getName(), parameter.getDefaultValue().get()));
                }
            }
            return result;
        } else {
            return arguments;
        }
    }

    private boolean isNamedArguments(List<Argument> arguments) {
        for (Argument argument : arguments) {
            if (argument.getName() != null) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesNamedArguments(List<Argument> arguments) {
        int n = 0;
        for (FunctionParameter parameter : parameters) {
            if (arguments.stream().anyMatch(e -> e.getName().equals(parameter.getName()))) {
                n++;
            } else {
                if (!parameter.getDefaultValue().isPresent()) {
                    return false;
                }
            }
        }
        return n == arguments.size();
    }

}
