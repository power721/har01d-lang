package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.LocalVariableReference;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.PrintStatement;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;
import org.objectweb.asm.MethodVisitor;

public class StatementGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final PrintStatementGenerator printStatementGenerator;
    private final ReferenceExpressionGenerator referenceExpressionGenerator;
    private final AssignmentStatementGenerator assignmentStatementGenerator;
    private final VariableDeclarationStatementGenerator variableDeclarationStatementGenerator;

    public StatementGenerator(MethodVisitor methodVisitor, Scope scope) {
        expressionGenerator = new ExpressionGenerator(methodVisitor, scope);
        referenceExpressionGenerator = new ReferenceExpressionGenerator(methodVisitor, scope);
        assignmentStatementGenerator = new AssignmentStatementGenerator(methodVisitor, expressionGenerator, scope);
        printStatementGenerator = new PrintStatementGenerator(expressionGenerator, methodVisitor);
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

}
