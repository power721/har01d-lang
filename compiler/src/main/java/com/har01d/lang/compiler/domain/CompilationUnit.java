package com.har01d.lang.compiler.domain;

import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.statement.Statement;
import java.util.List;

public class CompilationUnit {

    private final List<Statement> statements;
    private final List<Function> functions;
    private final ClassDeclaration classDeclaration;
    private final Scope scope;

    public CompilationUnit(List<Statement> statements,
        List<Function> functions, ClassDeclaration classDeclaration, Scope scope) {
        this.statements = statements;
        this.functions = functions;
        this.classDeclaration = classDeclaration;
        this.scope = scope;
    }

    public List<Statement> getStatements() {
        return statements;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    public Scope getScope() {
        return scope;
    }

}
