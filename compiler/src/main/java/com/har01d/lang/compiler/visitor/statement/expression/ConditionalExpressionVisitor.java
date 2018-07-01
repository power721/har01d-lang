package com.har01d.lang.compiler.visitor.statement.expression;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.ConditionalExpressionContext;
import com.har01d.lang.compiler.domain.CompareSign;
import com.har01d.lang.compiler.domain.statement.expression.ConditionalExpression;
import com.har01d.lang.compiler.domain.statement.expression.Expression;

public class ConditionalExpressionVisitor extends Har01dBaseVisitor<ConditionalExpression> {

    private final ExpressionVisitor expressionVisitor;

    public ConditionalExpressionVisitor(
        ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public ConditionalExpression visitConditionalExpression(ConditionalExpressionContext ctx) {
        Expression leftExpression = ctx.expression(0).accept(expressionVisitor);
        Expression rightExpression = ctx.expression(1).accept(expressionVisitor);
        CompareSign compareSign = CompareSign.fromString(ctx.cmp.getText());
        return new ConditionalExpression(leftExpression, rightExpression, compareSign);
    }

}
