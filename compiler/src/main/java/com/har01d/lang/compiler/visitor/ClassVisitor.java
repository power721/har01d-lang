package com.har01d.lang.compiler.visitor;

import java.util.List;
import java.util.stream.Collectors;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.ClassDeclaration;
import com.har01d.lang.compiler.domain.MetaData;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.visitor.function.FunctionSignatureVisitor;
import com.har01d.lang.compiler.visitor.function.FunctionVisitor;

public class ClassVisitor extends Har01dBaseVisitor<ClassDeclaration> {

    @Override
    public ClassDeclaration visitClassDeclaration(Har01dParser.ClassDeclarationContext ctx) {
        String className = ctx.className().getText();
        Scope scope = new Scope(new MetaData(className, true));
        FunctionSignatureVisitor functionSignatureVisitor = new FunctionSignatureVisitor(scope);

        List<Har01dParser.FunctionContext> contexts = ctx.classBody().function();
        for (Har01dParser.FunctionContext functionContext : contexts) {
            FunctionSignature signature = functionContext.functionDeclaration().accept(functionSignatureVisitor);
            scope.addSignature(signature);
        }

        List<Function> functions = contexts.stream().map(e -> e.accept(new FunctionVisitor(scope)))
                                        .collect(Collectors.toList());

        return new ClassDeclaration(className, functions);
    }

}
