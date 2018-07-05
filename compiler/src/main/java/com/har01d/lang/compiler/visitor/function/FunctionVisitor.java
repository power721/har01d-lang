package com.har01d.lang.compiler.visitor.function;

import java.util.Collections;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.FunctionContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.visitor.statement.BlockStatementVisitor;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class FunctionVisitor extends Har01dBaseVisitor<Function> {

    private final Scope scope;
    private FunctionSignature functionSignature;

    public FunctionVisitor(Scope scope, FunctionSignature functionSignature) {
        this.scope = scope;
        this.functionSignature = functionSignature;
    }

    @Override
    public Function visitFunction(FunctionContext ctx) {
        Scope scope = new Scope(this.scope, true);
        if (functionSignature == null) {
            functionSignature = ctx.functionDeclaration().accept(new FunctionSignatureVisitor(scope));
        }

        if (scope.isClassDeclaration()) {
            scope.addLocalValue("this", scope.getClassType(), true, ctx);
        }

        scope.setFunctionName(functionSignature.getInternalName());
        functionSignature.getParameters().forEach(e -> scope.addLocalValue(e.getName(), e.getType(), true, ctx));
        Block block = null;
        if (ctx.block() != null) {
            BlockStatementVisitor blockStatementVisitor = new BlockStatementVisitor(scope);
            block = ctx.block().accept(blockStatementVisitor);
        } else if (ctx.expression() != null) {
            ExpressionVisitor expressionVisitor = new ExpressionVisitor(scope);
            Expression expression = ctx.expression().accept(expressionVisitor);
            if (ctx.functionDeclaration().type() == null) {
                functionSignature.setReturnType(expression.getType());
            }
            block = new Block(scope, Collections.emptyList(), Collections.singletonList(expression));
        }

        if (block != null) {
            block.getScope().getImplicitVariables().forEach(e -> functionSignature.addImplicitParameter(e));
        }

        // TODO: constructor
        return new Function(functionSignature, block);
    }

}
