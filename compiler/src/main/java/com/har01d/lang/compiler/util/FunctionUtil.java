package com.har01d.lang.compiler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;
import com.har01d.lang.compiler.visitor.function.FunctionSignatureVisitor;
import com.har01d.lang.compiler.visitor.function.FunctionVisitor;

public final class FunctionUtil {

    public static List<FunctionSignature> getFunctionSignatures(List<Har01dParser.FunctionContext> contexts,
                                    Scope scope) {
        FunctionSignatureVisitor functionSignatureVisitor = new FunctionSignatureVisitor(scope);
        List<FunctionSignature> signatures = new ArrayList<>();
        for (Har01dParser.FunctionContext functionContext : contexts) {
            FunctionSignature signature = functionContext.functionDeclaration().accept(functionSignatureVisitor);
            if (scope.isSignatureExist(signature)) {
                throw new InvalidSyntaxException("Function " + signature.getName()
                                                + signature.getParameters().stream().map(e -> e.getType().getName())
                                                                                .collect(Collectors.joining(", ", "(",
                                                                                                                ")"))
                                                + " already declared!", functionContext.functionDeclaration());
            }
            scope.addSignature(signature);
            signatures.add(signature);
        }
        return signatures;
    }

    public static List<Function> getFunctions(List<Har01dParser.FunctionContext> contexts, Scope scope) {
        List<FunctionSignature> signatures = FunctionUtil.getFunctionSignatures(contexts, scope);

        int i = 0;
        List<Function> functions = new ArrayList<>();
        for (Har01dParser.FunctionContext functionContext : contexts) {
            functions.add(functionContext.accept(new FunctionVisitor(scope, signatures.get(i++))));
        }

        return functions;
    }

    public static List<Function> getConstructors(List<Har01dParser.ConstructorContext> contexts, Scope scope) {
        return contexts.stream().map(e -> e.accept(new FunctionVisitor(scope, null))).collect(Collectors.toList());
    }

}
