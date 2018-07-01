package com.har01d.lang.compiler.visitor.statement;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.BlockContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class BlockStatementVisitor extends Har01dBaseVisitor<Block> {

    private final Scope scope;

    public BlockStatementVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Block visitBlock(BlockContext ctx) {
        Scope newScope = new Scope(scope);
        StatementVisitor statementVisitor = new StatementVisitor(newScope);
        List<Statement> statements = ctx.statement().stream().map(e -> e.accept(statementVisitor))
            .collect(Collectors.toList());
        return new Block(scope, statements);
    }

}