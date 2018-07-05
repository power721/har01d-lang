package com.har01d.lang.compiler.visitor.statement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.BlockContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.util.FunctionUtil;
import com.har01d.lang.compiler.visitor.function.FunctionVisitor;

public class BlockStatementVisitor extends Har01dBaseVisitor<Block> {

    private final Scope scope;

    public BlockStatementVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Block visitBlock(BlockContext ctx) {
        Scope scope = new Scope(this.scope);
        StatementVisitor statementVisitor = new StatementVisitor(scope);

        List<FunctionSignature> signatures = FunctionUtil.getFunctionSignatures(ctx.function(), scope);

        List<Statement> statements = ctx.statement().stream().map(e -> e.accept(statementVisitor))
                                        .collect(Collectors.toList());

        List<Function> functions = new ArrayList<>();
        int i = 0;
        for (Har01dParser.FunctionContext functionContext : ctx.function()) {
            functions.add(functionContext.accept(new FunctionVisitor(scope, signatures.get(i++))));
        }

        return new Block(scope, functions, statements);
    }

}
