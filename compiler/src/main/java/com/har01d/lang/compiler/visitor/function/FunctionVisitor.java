package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.FunctionContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.visitor.statement.StatementVisitor;

public class FunctionVisitor extends Har01dBaseVisitor<Function> {

    private final Scope scope;

    public FunctionVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Function visitFunction(FunctionContext ctx) {
        Scope scope = new Scope(this.scope);
        FunctionSignature functionSignature = ctx.functionDeclaration().accept(new FunctionSignatureVisitor(scope));

        if (scope.isClassDeclaration()) {
            scope.addLocalValue("this", scope.getClassType(), true, ctx);
        }

        StatementVisitor statementVisitor = new StatementVisitor(scope);
        functionSignature.getParameters().forEach(e -> scope.addLocalValue(e.getName(), e.getType(), false, ctx));
        Statement block = ctx.block().accept(statementVisitor);
        // TODO: constructor
        return new Function(functionSignature, block);
    }

}
