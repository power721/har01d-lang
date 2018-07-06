package com.har01d.lang.compiler.visitor.statement.expression;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.LogicalOperator;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.expression.LogicalExpression;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;

public class LogicalExpressionVisitor extends Har01dBaseVisitor<LogicalExpression> {

    private final ExpressionVisitor expressionVisitor;

    public LogicalExpressionVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public LogicalExpression visitLogicalExpression(Har01dParser.LogicalExpressionContext ctx) {
        LogicalOperator operator = LogicalOperator.valueOf(ctx.op.getText().toUpperCase());
        Expression leftExpression = ctx.expression(0).accept(expressionVisitor);
        if (leftExpression.getType() != BuiltInType.BOOLEAN && !leftExpression.getType().equals(ClassType.Boolean())) {
            throw new InvalidSyntaxException("Expression type is not boolean!", ctx.expression(0));
        }

        Expression rightExpression = null;
        if (operator != LogicalOperator.NOT) {
            rightExpression = ctx.expression(1).accept(expressionVisitor);
            if (rightExpression.getType() != BuiltInType.BOOLEAN
                                            && !rightExpression.getType().equals(ClassType.Boolean())) {
                throw new InvalidSyntaxException("Expression type is not boolean!", ctx.expression(1));
            }
        }

        return new LogicalExpression(operator, leftExpression, rightExpression);
    }

}
