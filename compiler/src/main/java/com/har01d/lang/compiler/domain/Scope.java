package com.har01d.lang.compiler.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scope {

    private final Map<String, LocalVariable> localVariables;
    private final List<String> localVariablesIndex;

    public Scope() {
        localVariables = new HashMap<>();
        localVariablesIndex = new ArrayList<>();
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
        localVariables.put(localVariable.getName(), localVariable);
        localVariablesIndex.add(localVariable.getName());
    }

}
