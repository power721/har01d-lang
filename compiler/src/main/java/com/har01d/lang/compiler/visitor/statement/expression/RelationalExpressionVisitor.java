package com.har01d.lang.compiler.visitor.statement.expression;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.RelationalExpressionContext;
import com.har01d.lang.compiler.domain.CompareSign;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;

public class RelationalExpressionVisitor extends Har01dBaseVisitor<RelationalExpression> {

    private final ExpressionVisitor expressionVisitor;

    public RelationalExpressionVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public RelationalExpression visitRelationalExpression(RelationalExpressionContext ctx) {
        Expression leftExpression = ctx.expression(0).accept(expressionVisitor);
        Expression rightExpression = ctx.expression(1).accept(expressionVisitor);
        CompareSign compareSign = CompareSign.fromString(ctx.cmp.getText());

        switch (compareSign) {
            case LESS:
            case GREATER:
            case LESS_OR_EQUAL:
            case GRATER_OR_EQUAL:
                boolean isString1 = leftExpression.getType() == BuiltInType.STRING;
                boolean isString2 = rightExpression.getType() == BuiltInType.STRING;
                if (isString1 && !isString2) {
                    throw new InvalidSyntaxException(
                                                    "Cannot compare string with " + rightExpression.getType().getName(),
                                                    ctx);
                }
                if (!isString1 && isString2) {
                    throw new InvalidSyntaxException("Cannot compare " + leftExpression.getType().getName()
                                                    + " with string", ctx);
                }
                break;
        }

        return new RelationalExpression(leftExpression, rightExpression, compareSign);
    }

}
