package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.ArgumentContext;
import com.har01d.lang.compiler.domain.function.Argument;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class ArgumentExpressionVisitor extends Har01dBaseVisitor<Argument> {

    private final ExpressionVisitor expressionVisitor;

    public ArgumentExpressionVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public Argument visitArgument(ArgumentContext ctx) {
        Expression expression = ctx.expression().accept(expressionVisitor);
        return new Argument(null, expression);
    }

    @Override
    public Argument visitNamedArgument(Har01dParser.NamedArgumentContext ctx) {
        String name = ctx.name().getText();
        Expression expression = ctx.expression().accept(expressionVisitor);
        return new Argument(name, expression);
    }

}
