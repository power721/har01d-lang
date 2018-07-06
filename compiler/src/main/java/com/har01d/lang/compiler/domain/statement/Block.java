package com.har01d.lang.compiler.domain.statement;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.generator.StatementGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Block implements Statement {

    private final Scope scope;
    private final List<Function> functions;
    private final List<Statement> statements;

    public Block(Scope scope, List<Function> functions, List<Statement> statements) {
        this.scope = scope;
        this.functions = functions;
        this.statements = statements;
    }

    public static Block empty(Scope scope) {
        return new Block(scope, new ArrayList<>(), new ArrayList<>());
    }

    public Scope getScope() {
        return scope;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public List<Statement> getStatements() {
        return Collections.unmodifiableList(statements);
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
