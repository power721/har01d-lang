package com.har01d.lang.compiler.visitor.statement;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.AddContext;
import com.har01d.lang.antlr.Har01dParser.AssignmentContext;
import com.har01d.lang.antlr.Har01dParser.BlockContext;
import com.har01d.lang.antlr.Har01dParser.FunctionCallContext;
import com.har01d.lang.antlr.Har01dParser.MultiplyContext;
import com.har01d.lang.antlr.Har01dParser.PrintContext;
import com.har01d.lang.antlr.Har01dParser.ReturnVoidContext;
import com.har01d.lang.antlr.Har01dParser.ReturnWithValueContext;
import com.har01d.lang.antlr.Har01dParser.ValueDeclarationContext;
import com.har01d.lang.antlr.Har01dParser.VariableDeclarationContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.visitor.function.ReturnStatementVisitor;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;

public class StatementVisitor extends Har01dBaseVisitor<Statement> {

    private final PrintStatementVisitor printStatementVisitor;
    private final ReturnStatementVisitor returnStatementVisitor;
    private final BlockStatementVisitor blockStatementVisitor;
    private final AssignmentStatementVisitor assignmentStatementVisitor;
    private final VariableDeclarationStatementVisitor variableDeclarationStatementVisitor;
    private final ExpressionVisitor expressionVisitor;

    public StatementVisitor(Scope scope) {
        expressionVisitor = new ExpressionVisitor(scope);
        printStatementVisitor = new PrintStatementVisitor(expressionVisitor);
        blockStatementVisitor = new BlockStatementVisitor(scope);
        returnStatementVisitor = new ReturnStatementVisitor(expressionVisitor);
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

    @Override
    public Statement visitReturnVoid(ReturnVoidContext ctx) {
        return returnStatementVisitor.visitReturnVoid(ctx);
    }

    @Override
    public Statement visitReturnWithValue(ReturnWithValueContext ctx) {
        return returnStatementVisitor.visitReturnWithValue(ctx);
    }

    @Override
    public Statement visitBlock(BlockContext ctx) {
        return blockStatementVisitor.visitBlock(ctx);
    }

    @Override
    public Expression visitFunctionCall(FunctionCallContext ctx) {
        return expressionVisitor.visitFunctionCall(ctx);
    }

    @Override
    public Expression visitAdd(AddContext ctx) {
        return expressionVisitor.visitAdd(ctx);
    }

    @Override
    public Expression visitMultiply(MultiplyContext ctx) {
        return expressionVisitor.visitMultiply(ctx);
    }

}
