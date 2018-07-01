package com.har01d.lang.compiler.visitor.statement.expression;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.RelationalExpressionContext;
import com.har01d.lang.compiler.domain.CompareSign;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;

public class RelationalExpressionVisitor extends Har01dBaseVisitor<RelationalExpression> {

    private final ExpressionVisitor expressionVisitor;

    public RelationalExpressionVisitor(
        ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public RelationalExpression visitRelationalExpression(RelationalExpressionContext ctx) {
        Expression leftExpression = ctx.expression(0).accept(expressionVisitor);
        Expression rightExpression = ctx.expression(1).accept(expressionVisitor);
        CompareSign compareSign = CompareSign.fromString(ctx.cmp.getText());
        return new RelationalExpression(leftExpression, rightExpression, compareSign);
    }

}
