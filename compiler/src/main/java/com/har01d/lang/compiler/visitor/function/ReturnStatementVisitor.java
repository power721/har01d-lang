package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.ReturnVoidContext;
import com.har01d.lang.antlr.Har01dParser.ReturnWithValueContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.ReturnStatement;
import com.har01d.lang.compiler.domain.statement.expression.EmptyExpression;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class ReturnStatementVisitor extends Har01dBaseVisitor<ReturnStatement> {

    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    public ReturnStatementVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
        this.expressionVisitor = expressionVisitor;
        this.scope = scope;
    }

    @Override
    public ReturnStatement visitReturnVoid(ReturnVoidContext ctx) {
        return new ReturnStatement(new EmptyExpression(BuiltInType.VOID), BuiltInType.VOID);
    }

    @Override
    public ReturnStatement visitReturnWithValue(ReturnWithValueContext ctx) {
        Expression expression = ctx.accept(expressionVisitor);
        return new ReturnStatement(expression, getType(expression));
    }

    private Type getType(Expression expression) {
        if (scope.getFunctionSignature() != null) {
            return scope.getFunctionSignature().getReturnType();
        }
        return expression.getType();
    }

}
