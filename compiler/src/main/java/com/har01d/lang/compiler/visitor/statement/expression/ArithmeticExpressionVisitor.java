package com.har01d.lang.compiler.visitor.statement.expression;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.AddContext;
import com.har01d.lang.antlr.Har01dParser.MultiplyContext;
import com.har01d.lang.antlr.Har01dParser.PowerContext;
import com.har01d.lang.compiler.domain.statement.expression.Addition;
import com.har01d.lang.compiler.domain.statement.expression.ArithmeticExpression;
import com.har01d.lang.compiler.domain.statement.expression.Division;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
import com.har01d.lang.compiler.domain.statement.expression.Power;
import com.har01d.lang.compiler.domain.statement.expression.Remainder;
import com.har01d.lang.compiler.domain.statement.expression.Subtraction;

public class ArithmeticExpressionVisitor extends Har01dBaseVisitor<ArithmeticExpression> {

    private final ExpressionVisitor expressionVisitor;

    public ArithmeticExpressionVisitor(
        ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public ArithmeticExpression visitAdd(AddContext ctx) {
        Expression leftExpression = ctx.expression(0).accept(expressionVisitor);
        Expression rightExpression = ctx.expression(1).accept(expressionVisitor);
        String operator = ctx.op.getText();
        switch (operator) {
            case "+":
                return new Addition(leftExpression, rightExpression);
            case "-":
                return new Subtraction(leftExpression, rightExpression);
            default:
                throw new RuntimeException("invalid operator " + operator);
        }
    }

    @Override
    public ArithmeticExpression visitMultiply(MultiplyContext ctx) {
        Expression leftExpression = ctx.expression(0).accept(expressionVisitor);
        Expression rightExpression = ctx.expression(1).accept(expressionVisitor);
        String operator = ctx.op.getText();
        switch (operator) {
            case "*":
                return new Multiplication(leftExpression, rightExpression);
            case "/":
                return new Division(leftExpression, rightExpression);
            case "%":
                return new Remainder(leftExpression, rightExpression);
            default:
                throw new RuntimeException("invalid operator " + operator);
        }
    }

    @Override
    public ArithmeticExpression visitPower(PowerContext ctx) {
        Expression leftExpression = ctx.expression(0).accept(expressionVisitor);
        Expression rightExpression = ctx.expression(1).accept(expressionVisitor);
        return new Power(leftExpression, rightExpression);
    }

}
