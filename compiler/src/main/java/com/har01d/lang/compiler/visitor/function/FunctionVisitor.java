package com.har01d.lang.compiler.visitor.function;

import java.util.ArrayList;
import java.util.List;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.FunctionContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.visitor.statement.StatementVisitor;

public class FunctionVisitor extends Har01dBaseVisitor<Function> {

    private final Scope scope;

    public FunctionVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Function visitFunction(FunctionContext ctx) {
        Scope scope = new Scope(this.scope);
        FunctionSignature functionSignature = ctx.functionDeclaration().accept(new FunctionSignatureVisitor(scope));

        if (scope.isClassDeclaration()) {
            scope.addLocalValue("this", scope.getClassType(), true, ctx);
        }

        StatementVisitor statementVisitor = new StatementVisitor(scope);
        functionSignature.getParameters().forEach(e -> scope.addLocalValue(e.getName(), e.getType(), true, ctx));
        Statement block = null;
        if (ctx.block() != null) {
            block = ctx.block().accept(statementVisitor);
        } else if (ctx.expression() != null) {
            List<Statement> statements = new ArrayList<>();
            Expression expression = ctx.expression().accept(statementVisitor.getExpressionVisitor());
            statements.add(expression);
            if (ctx.functionDeclaration().type() == null) {
                functionSignature.setReturnType(expression.getType());
            }
            block = new Block(scope, statements);
        }

        // TODO: constructor
        return new Function(functionSignature, block);
    }

}
