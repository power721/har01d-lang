package com.har01d.lang.compiler.visitor.statement.loop;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.loop.RangedForStatement;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;
import com.har01d.lang.compiler.util.TypeResolver;
import com.har01d.lang.compiler.visitor.statement.StatementVisitor;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class ForStatementVisitor extends Har01dBaseVisitor<RangedForStatement> {

    private final ExpressionVisitor expressionVisitor;
    private final Scope scope;

    public ForStatementVisitor(ExpressionVisitor expressionVisitor, Scope scope) {
        this.expressionVisitor = expressionVisitor;
        this.scope = scope;
    }

    @Override
    public RangedForStatement visitForStatement(Har01dParser.ForStatementContext ctx) {
        Scope scope = new Scope(this.scope);
        String varName = ctx.forConditions().iterator.getText();

        Statement iterator;
        Expression startExpression = ctx.forConditions().startExpr.accept(expressionVisitor);
        Expression endExpression = ctx.forConditions().endExpr.accept(expressionVisitor);
        if (scope.isLocalVariableExists(varName)) {
            iterator = new Assignment(varName, startExpression);
            scope.getVariable(varName).setInitialized(true);
        } else {
            iterator = new VariableDeclaration(varName, startExpression);
            scope.addLocalValue(varName, startExpression.getType(), true, ctx);
        }

        StatementVisitor statementVisitor = new StatementVisitor(scope);
        Statement statement = ctx.statement().accept(statementVisitor);
        return new RangedForStatement(iterator, scope.getVariable(varName), endExpression, getStep(ctx), statement);
    }

    private int getStep(Har01dParser.ForStatementContext ctx) {
        int step = 1;
        if (ctx.forConditions().step != null) {
            Type type = TypeResolver.resolve(ctx.forConditions().step);
            if (type != BuiltInType.INT) {
                throw new InvalidSyntaxException("Step must be integer!", ctx.forConditions().step);
            }

            String value = ctx.forConditions().step.getText();
            step = Integer.decode(value);

            if (step == 0) {
                throw new InvalidSyntaxException("Step cannot be zero!", ctx.forConditions().step);
            }
        }
        return step;
    }

}
