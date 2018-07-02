package com.har01d.lang.compiler.visitor.statement;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.statement.IfStatement;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class IfStatementVisitor extends Har01dBaseVisitor<IfStatement> {

    private final ExpressionVisitor expressionVisitor;
    private final StatementVisitor statementVisitor;

    public IfStatementVisitor(ExpressionVisitor expressionVisitor, StatementVisitor statementVisitor) {
        this.expressionVisitor = expressionVisitor;
        this.statementVisitor = statementVisitor;
    }

    @Override
    public IfStatement visitIfStatement(Har01dParser.IfStatementContext ctx) {
        Expression condition = ctx.expression().accept(expressionVisitor);
        Statement trueStatement = ctx.trueStatement.accept(statementVisitor);
        Statement falseStatement = null;
        if (ctx.falseStatement != null) {
            falseStatement = ctx.falseStatement.accept(statementVisitor);
        }
        return new IfStatement(condition, trueStatement, falseStatement);
    }

}
