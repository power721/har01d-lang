package com.har01d.lang.compiler.generator;

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
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
import com.har01d.lang.compiler.domain.statement.expression.Power;
import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;
import com.har01d.lang.compiler.domain.statement.expression.Remainder;
import com.har01d.lang.compiler.domain.statement.expression.Subtraction;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;

public class StatementGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final IfStatementGenerator ifStatementGenerator;
    private final BlockStatementGenerator blockStatementGenerator;
    private final PrintStatementGenerator printStatementGenerator;
    private final ReturnStatementGenerator returnStatementGenerator;
    private final ReferenceExpressionGenerator referenceExpressionGenerator;
    private final AssignmentStatementGenerator assignmentStatementGenerator;
    private final VariableDeclarationStatementGenerator variableDeclarationStatementGenerator;

    public StatementGenerator(MethodVisitor methodVisitor, Scope scope) {
        expressionGenerator = new ExpressionGenerator(methodVisitor, scope);
        blockStatementGenerator = new BlockStatementGenerator(methodVisitor);
        referenceExpressionGenerator = new ReferenceExpressionGenerator(methodVisitor, scope);
        printStatementGenerator = new PrintStatementGenerator(methodVisitor, expressionGenerator);
        returnStatementGenerator = new ReturnStatementGenerator(methodVisitor, expressionGenerator);
        assignmentStatementGenerator = new AssignmentStatementGenerator(methodVisitor, expressionGenerator, scope);
        ifStatementGenerator = new IfStatementGenerator(expressionGenerator, this, methodVisitor);
        variableDeclarationStatementGenerator = new VariableDeclarationStatementGenerator(this, expressionGenerator);
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

}
