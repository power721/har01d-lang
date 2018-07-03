package com.har01d.lang.compiler.visitor.statement;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.ValueDeclarationContext;
import com.har01d.lang.antlr.Har01dParser.VariableDeclarationContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.LocalValue;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
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
        Type type;
        Expression expression = null;
        if (ctx.expression() != null) {
            expression = ctx.expression().accept(expressionVisitor);
            type = expression.getType();
        } else {
            type = TypeResolver.resolve(ctx.type());
        }

        String varName = ctx.name().getText();
        scope.addLocalVariable(new LocalVariable(varName, type, expression != null), ctx);
        return new VariableDeclaration(varName, expression);
    }

    @Override
    public VariableDeclaration visitValueDeclaration(ValueDeclarationContext ctx) {
        Type type;
        Expression expression = null;
        if (ctx.expression() != null) {
            expression = ctx.expression().accept(expressionVisitor);
            type = expression.getType();
        } else {
            type = TypeResolver.resolve(ctx.type());
        }

        String varName = ctx.name().getText();
        scope.addLocalVariable(new LocalValue(varName, type, expression != null), ctx);
        return new VariableDeclaration(varName, expression);
    }

}
