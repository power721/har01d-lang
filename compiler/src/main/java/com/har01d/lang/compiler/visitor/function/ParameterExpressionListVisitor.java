package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.ParameterContext;
import com.har01d.lang.antlr.Har01dParser.ParameterWithDefaultValueContext;
import com.har01d.lang.antlr.Har01dParser.ParametersListContext;
import com.har01d.lang.compiler.domain.function.FunctionParameter;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ParameterExpressionListVisitor extends Har01dBaseVisitor<List<FunctionParameter>> {

    private final ExpressionVisitor expressionVisitor;

    public ParameterExpressionListVisitor(
        ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public List<FunctionParameter> visitParametersList(ParametersListContext ctx) {
        List<ParameterContext> parameterContexts = ctx.parameter();
        List<FunctionParameter> parameters = new ArrayList<>();
        ParameterExpressionVisitor visitor = new ParameterExpressionVisitor(expressionVisitor);
        if (parameterContexts != null) {
            parameters.addAll(parameterContexts.stream().map(e -> e.accept(visitor)).collect(Collectors.toList()));
        }

        List<ParameterWithDefaultValueContext> paramsWithDefaultValueCtx = ctx.parameterWithDefaultValue();
        if (paramsWithDefaultValueCtx != null) {
            parameters
                .addAll(paramsWithDefaultValueCtx.stream().map(e -> e.accept(visitor)).collect(Collectors.toList()));
        }

        return parameters;
    }

}
