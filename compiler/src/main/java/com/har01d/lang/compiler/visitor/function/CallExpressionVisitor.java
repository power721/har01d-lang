package com.har01d.lang.compiler.visitor.function;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.ArgumentListContext;
import com.har01d.lang.antlr.Har01dParser.FunctionCallContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Argument;
import com.har01d.lang.compiler.domain.function.Call;
import com.har01d.lang.compiler.domain.function.ConstructorCall;
import com.har01d.lang.compiler.domain.function.FunctionCall;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class CallExpressionVisitor extends Har01dBaseVisitor<Call> {

    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    public CallExpressionVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
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

        // TODO: get FunctionSignature from class path
        FunctionSignature signature = scope.getSignature(name, arguments);

        //        if (signature == null) {
        //            FunctionReference functionReference = scope.getFunctionReference(name);
        //            if (functionReference != null) {
        //                return new FunctionCall(functionReference, functionReference.getFunctionSignature(), arguments);
        //            }
        //        }

        if (signature == null) {
            throw new InvalidSyntaxException("Cannot find function " + name
                                            + arguments.stream().map(e -> e.getType().getName()).collect(
                                                                            Collectors.joining(", ", "(", ")"))
                                            + "!", ctx);
        }

        Expression owner = null;
        if (ctx.owner != null) {
            owner = ctx.owner.accept(expressionVisitor);
        } else if (scope.isClassDeclaration()) {
            LocalVariable thisVariable = new LocalVariable("this", new ClassType(scope.getClassName()), true, true,
                                            scope);
            owner = new LocalVariableReference(thisVariable);
        }

        return new FunctionCall(owner, signature, signature.getArguments(arguments));
    }

    @Override
    public Call visitConstructorCall(Har01dParser.ConstructorCallContext ctx) {
        String name = ctx.qualifiedName().getText();
        ArgumentListContext argumentListContext = ctx.argumentList();
        List<Argument> arguments;
        if (argumentListContext != null) {
            ArgumentExpressionsListVisitor visitor = new ArgumentExpressionsListVisitor(expressionVisitor);
            arguments = argumentListContext.accept(visitor);
        } else {
            arguments = Collections.emptyList();
        }

        FunctionSignature signature = scope.getConstructor(name, arguments);

        if (signature == null) {
            throw new InvalidSyntaxException("Cannot find constructor " + name
                                            + arguments.stream().map(e -> e.getType().getName()).collect(
                                                                            Collectors.joining(", ", "(", ")"))
                                            + "!", ctx);
        }

        return new ConstructorCall(signature, arguments);
    }

}
