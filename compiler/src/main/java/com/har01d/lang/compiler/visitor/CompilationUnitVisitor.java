package com.har01d.lang.compiler.visitor;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.CompilationUnitContext;
import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.domain.MetaData;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.visitor.function.FunctionVisitor;
import com.har01d.lang.compiler.visitor.statement.StatementVisitor;
import java.util.List;
import java.util.stream.Collectors;

public class CompilationUnitVisitor extends Har01dBaseVisitor<CompilationUnit> {

    private final MetaData metaData;

    public CompilationUnitVisitor(MetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public CompilationUnit visitCompilationUnit(CompilationUnitContext ctx) {
        Scope scope = new Scope(metaData);
        FunctionVisitor functionVisitor = new FunctionVisitor(scope);
        List<Function> functions = ctx.function().stream().map(e -> e.accept(functionVisitor))
            .peek(e -> scope.addSignature(e.getFunctionSignature()))
            .collect(Collectors.toList());

        StatementVisitor statementVisitor = new StatementVisitor(scope);
        List<Statement> statements = ctx.statement().stream().map(e -> e.accept(statementVisitor))
            .collect(Collectors.toList());

        return new CompilationUnit(statements, functions, null, scope);
    }

}
