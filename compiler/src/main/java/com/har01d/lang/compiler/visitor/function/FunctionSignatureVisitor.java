package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.FunctionDeclarationContext;
import com.har01d.lang.antlr.Har01dParser.ParametersListContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;
import java.util.Collections;

public class FunctionSignatureVisitor extends Har01dBaseVisitor<FunctionSignature> {

    private final ExpressionVisitor expressionVisitor;

    public FunctionSignatureVisitor(Scope scope) {
        this.expressionVisitor = new ExpressionVisitor(scope);
    }

    @Override
    public FunctionSignature visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        String name = ctx.functionName().getText();
        Type type = TypeResolver.resolve(ctx.type());
        ParametersListContext parametersListContext = ctx.parametersList();
        if (parametersListContext != null) {
            ParameterExpressionListVisitor visitor = new ParameterExpressionListVisitor(expressionVisitor);
            return new FunctionSignature(name, parametersListContext.accept(visitor), type);
        }
        return new FunctionSignature(name, Collections.emptyList(), type);
    }

}
