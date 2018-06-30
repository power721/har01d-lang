package com.har01d.lang.compiler.visitor.statement;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.VariableReferenceContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;
import com.har01d.lang.compiler.domain.variable.Reference;

public class VariableReferenceExpressionVisitor extends Har01dBaseVisitor<Reference> {

    private final Scope scope;

    public VariableReferenceExpressionVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Reference visitVariableReference(VariableReferenceContext ctx) {
        // TODO: filed
        return new LocalVariableReference(scope.getLocalVariable(ctx.getText()));
    }

}
