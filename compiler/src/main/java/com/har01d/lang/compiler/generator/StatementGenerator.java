package com.har01d.lang.compiler.generator;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionCall;
import com.har01d.lang.compiler.domain.function.FunctionParameter;
import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.IfStatement;
import com.har01d.lang.compiler.domain.statement.PrintStatement;
import com.har01d.lang.compiler.domain.statement.ReturnStatement;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;
import com.har01d.lang.compiler.domain.statement.expression.Addition;
import com.har01d.lang.compiler.domain.statement.expression.Division;
import com.har01d.lang.compiler.domain.statement.expression.LogicalExpression;
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
import com.har01d.lang.compiler.domain.statement.expression.Power;
import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;
import com.har01d.lang.compiler.domain.statement.expression.Remainder;
import com.har01d.lang.compiler.domain.statement.expression.Subtraction;
import com.har01d.lang.compiler.domain.statement.loop.BreakStatement;
import com.har01d.lang.compiler.domain.statement.loop.RangedForStatement;
import com.har01d.lang.compiler.domain.statement.loop.WhileStatement;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;

public class StatementGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final IfStatementGenerator ifStatementGenerator;
    private final LoopStatementGenerator loopStatementGenerator;
    private final BlockStatementGenerator blockStatementGenerator;
    private final PrintStatementGenerator printStatementGenerator;
    private final ReturnStatementGenerator returnStatementGenerator;
    private final ReferenceExpressionGenerator referenceExpressionGenerator;
    private final AssignmentStatementGenerator assignmentStatementGenerator;
    private final VariableDeclarationStatementGenerator variableDeclarationStatementGenerator;

    public StatementGenerator(ClassWriter classWriter, MethodVisitor methodVisitor, Scope scope) {
        expressionGenerator = new ExpressionGenerator(methodVisitor, scope);
        blockStatementGenerator = new BlockStatementGenerator(classWriter, this);
        referenceExpressionGenerator = new ReferenceExpressionGenerator(methodVisitor);
        printStatementGenerator = new PrintStatementGenerator(methodVisitor, expressionGenerator);
        returnStatementGenerator = new ReturnStatementGenerator(methodVisitor, expressionGenerator);
        assignmentStatementGenerator = new AssignmentStatementGenerator(methodVisitor, expressionGenerator);
        ifStatementGenerator = new IfStatementGenerator(methodVisitor, this);
        loopStatementGenerator = new LoopStatementGenerator(classWriter, methodVisitor, this);
        variableDeclarationStatementGenerator = new VariableDeclarationStatementGenerator(this);
    }

    public void generate(PrintStatement printStatement) {
        printStatementGenerator.generate(printStatement);
    }

    public void generate(Literal value) {
        expressionGenerator.generate(value);
    }

    public void generate(Assignment assignment) {
        assignmentStatementGenerator.generator(assignment);
    }

    public void generate(VariableDeclaration variableDeclaration) {
        variableDeclarationStatementGenerator.generate(variableDeclaration);
    }

    public void generate(LocalVariableReference localVariableReference) {
        referenceExpressionGenerator.generate(localVariableReference);
    }

    public void generate(ReturnStatement returnStatement) {
        returnStatementGenerator.generate(returnStatement);
    }

    public void generate(Block block) {
        blockStatementGenerator.generate(block);
    }

    public void generate(FunctionParameter functionParameter) {
        expressionGenerator.generate(functionParameter);
    }

    public void generate(FunctionCall functionCall) {
        expressionGenerator.generate(functionCall);
    }

    public void generate(Addition expression) {
        expressionGenerator.generate(expression);
    }

    public void generate(Subtraction expression) {
        expressionGenerator.generate(expression);
    }

    public void generate(Multiplication expression) {
        expressionGenerator.generate(expression);
    }

    public void generate(Division expression) {
        expressionGenerator.generate(expression);
    }

    public void generate(Remainder expression) {
        expressionGenerator.generate(expression);
    }

    public void generate(Power expression) {
        expressionGenerator.generate(expression);
    }

    public void generate(RelationalExpression relationalExpression) {
        expressionGenerator.generate(relationalExpression);
    }

    public void generate(IfStatement ifStatement) {
        ifStatementGenerator.generate(ifStatement);
    }

    public void generate(LogicalExpression logicalExpression) {
        expressionGenerator.generate(logicalExpression);
    }

    public void generate(RangedForStatement rangedForStatement) {
        loopStatementGenerator.generate(rangedForStatement);
    }

    public void generate(WhileStatement statement) {
        loopStatementGenerator.generate(statement);
    }

    public void generate(BreakStatement breakStatement) {
        loopStatementGenerator.generate(breakStatement);
    }

}
