package com.har01d.lang.compiler.visitor.statement;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.VariableDeclarationContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class VariableDeclarationStatementVisitor extends Har01dBaseVisitor<VariableDeclaration> {

    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    public VariableDeclarationStatementVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
        this.expressionVisitor = expressionVisitor;
        this.scope = scope;
    }

    @Override
    public VariableDeclaration visitVariableDeclaration(VariableDeclarationContext ctx) {
        Type type = null;
        Expression expression = null;
        if (ctx.type() != null) {
            type = TypeResolver.resolve(ctx.type());
        }

        if (ctx.expression() != null) {
            expression = ctx.expression().accept(expressionVisitor);
            if (type == null) {
                type = expression.getType();
            }
        }

        String category = ctx.c.getText();
        String varName = ctx.name().getText();
        if ("var".equals(category)) {
            scope.addLocalVariable(varName, type, expression != null, ctx);
        } else {
            scope.addLocalValue(varName, type, expression != null, ctx);
        }
        return new VariableDeclaration(scope.getLocalVariable(varName), expression);
    }

}
