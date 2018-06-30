package com.har01d.lang.compiler.visitor;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.PrintContext;
import com.har01d.lang.compiler.domain.statement.Expression;
import com.har01d.lang.compiler.domain.statement.PrintStatement;

public class PrintStatementVisitor extends Har01dBaseVisitor<PrintStatement> {

    private final ExpressionVisitor expressionVisitor;

    public PrintStatementVisitor(ExpressionVisitor expressionVisitor) {
        this.expressionVisitor = expressionVisitor;
    }

    @Override
    public PrintStatement visitPrint(PrintContext ctx) {
        Expression expression = ctx.expression().accept(expressionVisitor);
        return new PrintStatement(expression);
    }

}
