package com.har01d.lang.compiler.visitor.statement.loop;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.loop.BreakStatement;

public class BreakStatementVisitor extends Har01dBaseVisitor<BreakStatement> {

    private final Scope scope;

    public BreakStatementVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public BreakStatement visitBreakStatement(Har01dParser.BreakStatementContext ctx) {
        return new BreakStatement(scope);
    }

}
