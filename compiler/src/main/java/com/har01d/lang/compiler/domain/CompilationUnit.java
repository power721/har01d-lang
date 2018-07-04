package com.har01d.lang.compiler.domain;

import java.util.List;

import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.statement.Statement;

public class CompilationUnit {

    private final List<Statement> statements;
    private final List<Function> functions;
    private final List<ClassDeclaration> classDeclarations;
    private final Scope scope;
    private final String name;

    public CompilationUnit(List<Statement> statements, List<Function> functions,
                                    List<ClassDeclaration> classDeclarations, Scope scope, String name) {
        this.statements = statements;
        this.functions = functions;
        this.classDeclarations = classDeclarations;
        this.scope = scope;
        this.name = name;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public List<ClassDeclaration> getClassDeclarations() {
        return classDeclarations;
    }

    public Scope getScope() {
        return scope;
    }

    public String getName() {
        return name;
    }

}
