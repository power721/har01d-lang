package com.har01d.lang.compiler.visitor.function;

import java.util.Optional;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.ParameterContext;
import com.har01d.lang.antlr.Har01dParser.ParameterWithDefaultValueContext;
import com.har01d.lang.compiler.domain.function.FunctionParameter;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class ParameterExpressionVisitor extends Har01dBaseVisitor<FunctionParameter> {

    private final ExpressionVisitor expressionVisitor;

    public ParameterExpressionVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public FunctionParameter visitParameter(ParameterContext ctx) {
        String name = ctx.ID().getText();
        Type type = TypeResolver.resolve(ctx.type());
        return new FunctionParameter(name, type);
    }

    @Override
    public FunctionParameter visitParameterWithDefaultValue(ParameterWithDefaultValueContext ctx) {
        String name = ctx.ID().getText();
        Type type = TypeResolver.resolve(ctx.type());
        Expression defaultValue = ctx.defaultValue.accept(expressionVisitor);
        return new FunctionParameter(name, type, Optional.of(defaultValue));
    }

}
