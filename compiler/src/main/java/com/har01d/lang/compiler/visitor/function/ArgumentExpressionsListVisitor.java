package com.har01d.lang.compiler.visitor.function;

import java.util.List;
import java.util.stream.Collectors;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.UnnamedArgumentsListContext;
import com.har01d.lang.compiler.domain.function.Argument;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class ArgumentExpressionsListVisitor extends Har01dBaseVisitor<List<Argument>> {

    private final ExpressionVisitor expressionVisitor;

    public ArgumentExpressionsListVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public List<Argument> visitUnnamedArgumentsList(UnnamedArgumentsListContext ctx) {
        ArgumentExpressionVisitor visitor = new ArgumentExpressionVisitor(expressionVisitor);
        return ctx.argument().stream().map(e -> e.accept(visitor)).collect(Collectors.toList());
    }

    @Override
    public List<Argument> visitNamedArgumentsList(Har01dParser.NamedArgumentsListContext ctx) {
        ArgumentExpressionVisitor visitor = new ArgumentExpressionVisitor(expressionVisitor);
        return ctx.namedArgument().stream().map(e -> e.accept(visitor)).collect(Collectors.toList());
    }

}
