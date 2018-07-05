package com.har01d.lang.compiler.visitor;

import java.util.ArrayList;
import java.util.List;

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

        List<FunctionSignature> signatures = new ArrayList<>();
        for (Har01dParser.FunctionContext functionContext : ctx.classBody().function()) {
            FunctionSignature signature = functionContext.functionDeclaration().accept(functionSignatureVisitor);
            scope.addSignature(signature);
            signatures.add(signature);
        }

        int i = 0;
        List<Function> functions = new ArrayList<>();
        for (Har01dParser.FunctionContext functionContext : ctx.classBody().function()) {
            functions.add(functionContext.accept(new FunctionVisitor(scope, signatures.get(i++))));
        }

        return new ClassDeclaration(className, functions);
    }

}
