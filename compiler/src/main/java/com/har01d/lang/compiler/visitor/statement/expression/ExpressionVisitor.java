package com.har01d.lang.compiler.visitor.statement.expression;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.AddContext;
import com.har01d.lang.antlr.Har01dParser.FunctionCallContext;
import com.har01d.lang.antlr.Har01dParser.LiteralContext;
import com.har01d.lang.antlr.Har01dParser.MultiplyContext;
import com.har01d.lang.antlr.Har01dParser.PowerContext;
import com.har01d.lang.antlr.Har01dParser.RelationalExpressionContext;
import com.har01d.lang.antlr.Har01dParser.VariableReferenceContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.visitor.function.CallExpressionVisitor;
import com.har01d.lang.compiler.visitor.statement.VariableReferenceExpressionVisitor;

public class ExpressionVisitor extends Har01dBaseVisitor<Expression> {

    private final CallExpressionVisitor callExpressionVisitor;
    private final LiteralExpressionVisitor literalExpressionVisitor;
    private final LogicalExpressionVisitor logicalExpressionVisitor;
    private final ArithmeticExpressionVisitor arithmeticExpressionVisitor;
    private final RelationalExpressionVisitor relationalExpressionVisitor;
    private final VariableReferenceExpressionVisitor variableReferenceExpressionVisitor;

    public ExpressionVisitor(Scope scope) {
        callExpressionVisitor = new CallExpressionVisitor(this, scope);
        arithmeticExpressionVisitor = new ArithmeticExpressionVisitor(this);
        relationalExpressionVisitor = new RelationalExpressionVisitor(this);
        variableReferenceExpressionVisitor = new VariableReferenceExpressionVisitor(scope);
        logicalExpressionVisitor = new LogicalExpressionVisitor(this);
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

    @Override
    public Expression visitConstructorCall(Har01dParser.ConstructorCallContext ctx) {
        return callExpressionVisitor.visitConstructorCall(ctx);
    }

    @Override
    public Expression visitFunctionCall(FunctionCallContext ctx) {
        return callExpressionVisitor.visitFunctionCall(ctx);
    }

    @Override
    public Expression visitAdd(AddContext ctx) {
        return arithmeticExpressionVisitor.visitAdd(ctx);
    }

    @Override
    public Expression visitMultiply(MultiplyContext ctx) {
        return arithmeticExpressionVisitor.visitMultiply(ctx);
    }

    @Override
    public Expression visitPower(PowerContext ctx) {
        return arithmeticExpressionVisitor.visitPower(ctx);
    }

    @Override
    public Expression visitRelationalExpression(RelationalExpressionContext ctx) {
        return relationalExpressionVisitor.visitRelationalExpression(ctx);
    }

    @Override
    public Expression visitLogicalExpression(Har01dParser.LogicalExpressionContext ctx) {
        return logicalExpressionVisitor.visitLogicalExpression(ctx);
    }

}
