package com.har01d.lang.compiler.domain;

import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.type.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Function {

    private final String name;
    private final List<FunctionParameter> arguments;
    private final List<Statement> statements;
    private final Type returnType;
    private Scope scope;

    public Function(Scope scope, String name, Type returnType, List<FunctionParameter> arguments,
        List<Statement> statements) {
        this.scope = scope;
        this.name = name;
        this.arguments = arguments;
        this.statements = statements;
        this.returnType = returnType;
    }

    public String getName() {
        return name;
    }

    public List<FunctionParameter> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

    public Collection<Statement> getStatements() {
        return Collections.unmodifiableCollection(statements);
    }

    public Scope getScope() {
        return scope;
    }

    public Type getReturnType() {
        return returnType;
    }

}
