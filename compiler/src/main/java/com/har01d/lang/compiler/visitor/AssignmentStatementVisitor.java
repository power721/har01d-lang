package com.har01d.lang.compiler.visitor;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.AssignmentContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.Expression;

public class AssignmentStatementVisitor extends Har01dBaseVisitor<Assignment> {

    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    public AssignmentStatementVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
        this.expressionVisitor = expressionVisitor;
        this.scope = scope;
    }

    @Override
    public Assignment visitAssignment(AssignmentContext ctx) {
        String varName = ctx.name().getText();
        if (scope.isLocalValue(varName)) {
            System.err.printf("variable '%s' cannot change value!", varName);
            System.exit(1);
        }

        Expression expression = ctx.expression().accept(expressionVisitor);
        // TODO: check type
        return new Assignment(varName, expression);
    }

}
