package com.har01d.lang.compiler.visitor.statement.loop;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.loop.WhileStatement;
import com.har01d.lang.compiler.visitor.statement.StatementVisitor;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class WhileStatementVisitor extends Har01dBaseVisitor<WhileStatement> {

    private final ExpressionVisitor expressionVisitor;
    private final StatementVisitor statementVisitor;

    public WhileStatementVisitor(ExpressionVisitor expressionVisitor, StatementVisitor statementVisitor) {
        this.expressionVisitor = expressionVisitor;
        this.statementVisitor = statementVisitor;
    }

    @Override
    public WhileStatement visitWhileStatement(Har01dParser.WhileStatementContext ctx) {
        Expression condition = ctx.expression().accept(expressionVisitor);
        Statement statement = ctx.statement().accept(statementVisitor);
        return new WhileStatement(condition, statement);
    }

}
