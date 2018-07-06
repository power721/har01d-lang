package com.har01d.lang.compiler.visitor.statement;

import java.util.stream.Collectors;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.AddContext;
import com.har01d.lang.antlr.Har01dParser.AssignmentContext;
import com.har01d.lang.antlr.Har01dParser.BlockContext;
import com.har01d.lang.antlr.Har01dParser.FunctionCallContext;
import com.har01d.lang.antlr.Har01dParser.MultiplyContext;
import com.har01d.lang.antlr.Har01dParser.PrintContext;
import com.har01d.lang.antlr.Har01dParser.RelationalExpressionContext;
import com.har01d.lang.antlr.Har01dParser.ReturnVoidContext;
import com.har01d.lang.antlr.Har01dParser.ReturnWithValueContext;
import com.har01d.lang.antlr.Har01dParser.VariableDeclarationContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.PrintStatement;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.loop.BreakStatement;
import com.har01d.lang.compiler.domain.statement.loop.ContinueStatement;
import com.har01d.lang.compiler.visitor.function.ReturnStatementVisitor;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;
import com.har01d.lang.compiler.visitor.statement.loop.ForStatementVisitor;
import com.har01d.lang.compiler.visitor.statement.loop.WhileStatementVisitor;

public class StatementVisitor extends Har01dBaseVisitor<Statement> {

    private final IfStatementVisitor ifStatementVisitor;
    private final ForStatementVisitor forStatementVisitor;
    private final WhileStatementVisitor whileStatementVisitor;
    private final BlockStatementVisitor blockStatementVisitor;
    private final ReturnStatementVisitor returnStatementVisitor;
    private final AssignmentStatementVisitor assignmentStatementVisitor;
    private final VariableDeclarationStatementVisitor variableDeclarationStatementVisitor;
    private final ExpressionVisitor expressionVisitor;

    public StatementVisitor(Scope scope) {
        expressionVisitor = new ExpressionVisitor(scope);
        blockStatementVisitor = new BlockStatementVisitor(scope);
        forStatementVisitor = new ForStatementVisitor(expressionVisitor, scope);
        returnStatementVisitor = new ReturnStatementVisitor(expressionVisitor, scope);
        ifStatementVisitor = new IfStatementVisitor(expressionVisitor, this);
        assignmentStatementVisitor = new AssignmentStatementVisitor(expressionVisitor, scope);
        whileStatementVisitor = new WhileStatementVisitor(expressionVisitor, this);
        variableDeclarationStatementVisitor = new VariableDeclarationStatementVisitor(expressionVisitor, scope);
    }

    @Override
    public Statement visitPrint(PrintContext ctx) {
        return new PrintStatement(ctx.expression().stream().map(e -> e.accept(expressionVisitor))
                                        .collect(Collectors.toList()));
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
    public Statement visitConstructorCall(Har01dParser.ConstructorCallContext ctx) {
        return expressionVisitor.visitConstructorCall(ctx);
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

    @Override
    public Statement visitPower(Har01dParser.PowerContext ctx) {
        return expressionVisitor.visitPower(ctx);
    }

    @Override
    public Expression visitRelationalExpression(RelationalExpressionContext ctx) {
        return expressionVisitor.visitRelationalExpression(ctx);
    }

    @Override
    public Statement visitLogicalExpression(Har01dParser.LogicalExpressionContext ctx) {
        return expressionVisitor.visitLogicalExpression(ctx);
    }

    @Override
    public Statement visitVariableReference(Har01dParser.VariableReferenceContext ctx) {
        return expressionVisitor.visitVariableReference(ctx);
    }

    @Override
    public Statement visitLiteral(Har01dParser.LiteralContext ctx) {
        return expressionVisitor.visitLiteral(ctx);
    }

    @Override
    public Statement visitIfStatement(Har01dParser.IfStatementContext ctx) {
        return ifStatementVisitor.visitIfStatement(ctx);
    }

    @Override
    public Statement visitForStatement(Har01dParser.ForStatementContext ctx) {
        return forStatementVisitor.visitForStatement(ctx);
    }

    @Override
    public Statement visitWhileLoop(Har01dParser.WhileLoopContext ctx) {
        return whileStatementVisitor.visitWhileLoop(ctx);
    }

    @Override
    public Statement visitDoWhileLoop(Har01dParser.DoWhileLoopContext ctx) {
        return whileStatementVisitor.visitDoWhileLoop(ctx);
    }

    @Override
    public Statement visitBreakStatement(Har01dParser.BreakStatementContext ctx) {
        return new BreakStatement();
    }

    @Override
    public Statement visitContinueStatement(Har01dParser.ContinueStatementContext ctx) {
        return new ContinueStatement();
    }

}
