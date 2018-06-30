package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.ArgumentListContext;
import com.har01d.lang.antlr.Har01dParser.FunctionCallContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Argument;
import com.har01d.lang.compiler.domain.function.Call;
import com.har01d.lang.compiler.domain.function.FunctionCall;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;
import java.util.Collections;
import java.util.List;

public class CallExpressionVisitor extends Har01dBaseVisitor<Call> {

    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    public CallExpressionVisitor(
        ExpressionVisitor expressionVisitor, Scope scope) {
        this.expressionVisitor = expressionVisitor;
        this.scope = scope;
    }

    @Override
    public Call visitFunctionCall(FunctionCallContext ctx) {
        String name = ctx.functionName().getText();
        ArgumentListContext argumentListContext = ctx.argumentList();
        List<Argument> arguments;
        if (argumentListContext != null) {
            ArgumentExpressionsListVisitor visitor = new ArgumentExpressionsListVisitor(expressionVisitor);
            arguments = argumentListContext.accept(visitor);
        } else {
            arguments = Collections.emptyList();
        }
        FunctionSignature signature = scope.getSignature(name, arguments);
        // TODO

        return new FunctionCall(null, signature, arguments);
    }

}
