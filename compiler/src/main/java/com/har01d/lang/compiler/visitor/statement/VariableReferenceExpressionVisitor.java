package com.har01d.lang.compiler.visitor.statement;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.VariableReferenceContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;
import com.har01d.lang.compiler.domain.variable.Reference;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;

public class VariableReferenceExpressionVisitor extends Har01dBaseVisitor<Reference> {

    private final Scope scope;

    public VariableReferenceExpressionVisitor(Scope scope) {
        this.scope = scope;
    }

    @Override
    public Reference visitVariableReference(VariableReferenceContext ctx) {
        // TODO: filed
        String name = ctx.getText();
        LocalVariable variable = scope.getLocalVariable(name);
        if (variable == null) {
            variable = scope.getVariable(name);
            if (variable != null) {
                scope.addImplicitVariable(variable);
                scope.addLocalValue(variable.getName(), variable.getType(), true, ctx);
            }
        }

        //        if (variable == null) {
        //            FunctionReference functionReference = scope.getFunctionReference(name);
        //            if (functionReference != null) {
        //                return functionReference;
        //            }
        //        }

        if (variable == null) {
            throw new InvalidSyntaxException("variable '" + name + "' doesn't declared!", ctx);
        }

        if (!variable.isInitialized()) {
            throw new InvalidSyntaxException("variable '" + name + "' doesn't initialized!", ctx);
        }

        return new LocalVariableReference(variable);
    }

}
