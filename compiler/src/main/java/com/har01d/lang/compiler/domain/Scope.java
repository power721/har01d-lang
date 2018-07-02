package com.har01d.lang.compiler.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.har01d.lang.compiler.domain.function.Argument;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.LocalValue;
import com.har01d.lang.compiler.domain.variable.LocalVariable;

public class Scope {

    private final MetaData metaData;
    private final Map<String, LocalVariable> localVariables;
    private final List<String> localVariablesIndex;
    private final List<FunctionSignature> functionSignatures;

    public Scope(MetaData metaData) {
        this.metaData = metaData;
        localVariables = new HashMap<>();
        localVariablesIndex = new ArrayList<>();
        functionSignatures = new ArrayList<>();
    }

    public Scope(Scope scope) {
        this.metaData = scope.metaData;
        localVariables = new HashMap<>(scope.localVariables);
        localVariablesIndex = new ArrayList<>(scope.localVariablesIndex);
        functionSignatures = new ArrayList<>(scope.functionSignatures);
    }

    public void addSignature(FunctionSignature functionSignature) {
        // TODO: check duplicate
        functionSignatures.add(functionSignature);
    }

    public FunctionSignature getSignature(String identifier, List<Argument> arguments) {
        return functionSignatures.stream().filter(e -> e.matches(identifier, arguments)).findFirst().orElseThrow(
                                        () -> new RuntimeException("Cannot find function " + identifier + arguments
                                                                        .stream().map(e -> e.getType().getName())
                                                                        .collect(Collectors.joining(", ", "(", ")"))));
    }

    public boolean isLocalVariableExists(String varName) {
        return localVariables.containsKey(varName);
    }

    public boolean isLocalValue(String varName) {
        LocalVariable localVariable = localVariables.get(varName);
        return localVariable instanceof LocalValue;
    }

    public LocalVariable getLocalVariable(String varName) {
        return localVariables.get(varName);
    }

    public int getLocalVariableIndex(String varName) {
        return localVariablesIndex.indexOf(varName);
    }

    public void addLocalVariable(LocalVariable localVariable) {
        if (!localVariables.containsKey(localVariable.getName())) {
            localVariables.put(localVariable.getName(), localVariable);
            localVariablesIndex.add(localVariable.getName());
        } else {
            throw new IllegalStateException("variable '" + localVariable.getName() + "' already declared!");
        }
    }

    public Type getClassType() {
        return new ClassType(metaData.getClassName());
    }

    public String getClassName() {
        return metaData.getClassName();
    }

    public boolean isClassDeclaration() {
        return metaData.isClassDeclaration();
    }

}
