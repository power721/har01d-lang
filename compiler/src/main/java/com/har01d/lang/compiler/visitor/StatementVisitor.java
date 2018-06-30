package com.har01d.lang.compiler.visitor;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.AssignmentContext;
import com.har01d.lang.antlr.Har01dParser.PrintContext;
import com.har01d.lang.antlr.Har01dParser.ValueDeclarationContext;
import com.har01d.lang.antlr.Har01dParser.VariableDeclarationContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Statement;

public class StatementVisitor extends Har01dBaseVisitor<Statement> {

    private final ExpressionVisitor expressionVisitor;
    private final PrintStatementVisitor printStatementVisitor;
    private final AssignmentStatementVisitor assignmentStatementVisitor;
    private final VariableDeclarationStatementVisitor variableDeclarationStatementVisitor;

    public StatementVisitor(Scope scope) {
        expressionVisitor = new ExpressionVisitor(scope);
        printStatementVisitor = new PrintStatementVisitor(expressionVisitor);
        assignmentStatementVisitor = new AssignmentStatementVisitor(expressionVisitor, scope);
        variableDeclarationStatementVisitor = new VariableDeclarationStatementVisitor(expressionVisitor, scope);
    }

    @Override
    public Statement visitPrint(PrintContext ctx) {
        return printStatementVisitor.visitPrint(ctx);
    }

    @Override
    public Statement visitAssignment(AssignmentContext ctx) {
        return assignmentStatementVisitor.visitAssignment(ctx);
    }

    @Override
    public Statement visitVariableDeclaration(VariableDeclarationContext ctx) {
        return variableDeclarationStatementVisitor.visitVariableDeclaration(ctx);
    }

    @Override
    public Statement visitValueDeclaration(ValueDeclarationContext ctx) {
        return variableDeclarationStatementVisitor.visitValueDeclaration(ctx);
    }

}
