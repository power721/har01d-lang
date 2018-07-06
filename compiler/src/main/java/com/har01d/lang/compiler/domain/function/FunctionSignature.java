package com.har01d.lang.compiler.domain.function;

import com.har01d.lang.compiler.domain.type.FunctionType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FunctionSignature {

    private final String name;
    private final String internalName;
    private final List<FunctionParameter> parameters;
    private final int flag;
    private Type returnType;

    public FunctionSignature(String name, String internalName, List<FunctionParameter> parameters, int flag,
        Type returnType) {
        this.name = name;
        this.internalName = internalName;
        this.parameters = parameters;
        this.flag = flag;
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public String getInternalName() {
        return internalName;
    }

    public int getFlag() {
        return flag;
    }

    public List<FunctionParameter> getParameters() {
        return Collections.unmodifiableList(parameters);
    }

    public void addImplicitParameter(LocalVariable variable) {
        parameters.add(new FunctionParameter(variable.getName(), variable.getType(),
                                        variable.getIndex(), Optional.of(new LocalVariableReference(variable))));
    }

    public Type getReturnType() {
        return returnType;
    }

    public FunctionType getFunctionType() {
        return new FunctionType(parameters.stream().map(FunctionParameter::getType).collect(Collectors.toList()),
                                        returnType);
    }

    public void setReturnType(Type returnType) {
        this.returnType = returnType;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        FunctionSignature signature = (FunctionSignature) o;

        if (!internalName.equals(signature.internalName))
            return false;
        return parameters.equals(signature.parameters);
    }

    @Override
    public int hashCode() {
        int result = internalName.hashCode();
        result = 31 * result + parameters.hashCode();
        return result;
    }

}
