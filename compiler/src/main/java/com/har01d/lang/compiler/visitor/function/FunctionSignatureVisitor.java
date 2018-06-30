package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.FunctionDeclarationContext;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;
import java.util.Collections;

public class FunctionSignatureVisitor extends Har01dBaseVisitor<FunctionSignature> {

    @Override
    public FunctionSignature visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        String name = ctx.functionName().getText();
        Type type = TypeResolver.resolve(ctx.type());
        // TODO:
        return new FunctionSignature(name, Collections.emptyList(), type);
    }

}
