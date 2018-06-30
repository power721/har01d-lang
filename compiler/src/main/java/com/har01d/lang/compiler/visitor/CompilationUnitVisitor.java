package com.har01d.lang.compiler.visitor;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.CompilationUnitContext;
import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class CompilationUnitVisitor extends Har01dBaseVisitor<CompilationUnit> {

    @Override
    public CompilationUnit visitCompilationUnit(CompilationUnitContext ctx) {
        Scope scope = new Scope();
        List<Har01dParser.StatementContext> statementContexts = ctx.statement();
        StatementVisitor statementVisitor = new StatementVisitor(scope);
        List<Statement> statements = statementContexts.stream().map(e -> e.accept(statementVisitor))
            .collect(Collectors.toList());

        return new CompilationUnit(statements, null, null, scope);
    }

}
