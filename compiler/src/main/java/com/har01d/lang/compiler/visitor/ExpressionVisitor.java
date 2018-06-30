package com.har01d.lang.compiler.visitor;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.LiteralContext;
import com.har01d.lang.antlr.Har01dParser.VariableReferenceContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Expression;

public class ExpressionVisitor extends Har01dBaseVisitor<Expression> {

    private final LiteralExpressionVisitor literalExpressionVisitor;
    private final VariableReferenceExpressionVisitor variableReferenceExpressionVisitor;

    public ExpressionVisitor(Scope scope) {
        variableReferenceExpressionVisitor = new VariableReferenceExpressionVisitor(scope);
        literalExpressionVisitor = new LiteralExpressionVisitor();
    }

    @Override
    public Expression visitLiteral(LiteralContext ctx) {
        return literalExpressionVisitor.visitLiteral(ctx);
    }

    @Override
    public Expression visitVariableReference(VariableReferenceContext ctx) {
        return variableReferenceExpressionVisitor.visitVariableReference(ctx);
    }

}
