package com.har01d.lang.compiler.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;

import com.har01d.lang.compiler.domain.function.Argument;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;

public class Scope {

    private final Scope parent;
    private final MetaData metaData;
    private final Map<String, LocalVariable> localVariables;
    private final List<String> localVariablesIndex;
    private final Set<LocalVariable> implicitVariables = new HashSet<>();
    private final List<FunctionSignature> functionSignatures;
    private final Set<String> classes;
    private String functionName;

    public Scope(MetaData metaData) {
        this.metaData = metaData;
        parent = null;
        localVariables = new HashMap<>();
        localVariablesIndex = new ArrayList<>();
        functionSignatures = new ArrayList<>();
        classes = new HashSet<>();
    }

    public Scope(Scope scope) {
        this(scope, false);
    }

    public Scope(Scope scope, boolean isFunction) {
        this.metaData = scope.metaData;
        parent = scope;
        if (!isFunction) {
            localVariables = new HashMap<>(scope.localVariables);
            localVariablesIndex = new ArrayList<>(scope.localVariablesIndex);
        } else {
            localVariables = new HashMap<>();
            localVariablesIndex = new ArrayList<>();
        }
        functionSignatures = new ArrayList<>(scope.functionSignatures);
        classes = new HashSet<>(scope.classes);
        functionName = scope.functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void addClass(String name) {
        classes.add(name);
    }

    public boolean isClassExist(String name) {
        return classes.contains(name);
    }

    public void addSignature(FunctionSignature functionSignature) {
        // TODO: check duplicate
        functionSignatures.add(functionSignature);
    }

    public FunctionSignature getSignature(String identifier, List<Argument> arguments) {
        for (FunctionSignature signature : functionSignatures) {
            if (signature.matches(identifier, arguments)) {
                return signature;
            }
        }

        if (parent != null) {
            return parent.getSignature(identifier, arguments);
        }

        return null;
    }

    public boolean isLocalVariableExists(String varName) {
        return localVariables.containsKey(varName);
    }

    public LocalVariable getLocalVariable(String varName) {
        return localVariables.get(varName);
    }

    public LocalVariable getVariable(String varName) {
        if (localVariables.containsKey(varName)) {
            return localVariables.get(varName);
        }

        if (parent != null) {
            return parent.getVariable(varName);
        }
        return null;
    }

    public int getLocalVariableIndex(String varName) {
        return localVariablesIndex.indexOf(varName);
    }

    public void addLocalVariable(String name, Type type, boolean initialized, ParserRuleContext ctx) {
        if (!localVariables.containsKey(name)) {
            localVariables.put(name, new LocalVariable(name, type, initialized, false));
            localVariablesIndex.add(name);
        } else {
            throw new InvalidSyntaxException("variable '" + name + "' already declared!", ctx);
        }
    }

    public void addLocalValue(String name, Type type, boolean initialized, ParserRuleContext ctx) {
        if (!localVariables.containsKey(name)) {
            localVariables.put(name, new LocalVariable(name, type, initialized, true));
            localVariablesIndex.add(name);
        } else {
            throw new InvalidSyntaxException("variable '" + name + "' already declared!", ctx);
        }
    }

    public void addImplicitVariable(LocalVariable variable) {
        implicitVariables.add(variable);
    }

    public Set<LocalVariable> getImplicitVariables() {
        return implicitVariables;
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
