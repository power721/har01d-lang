package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.Expression;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;

public class VariableDeclarationStatementGenerator {

    private final StatementGenerator statementGenerator;
    private final ExpressionGenerator expressionGenerator;

    public VariableDeclarationStatementGenerator(
        StatementGenerator statementGenerator, ExpressionGenerator expressionGenerator) {
        this.expressionGenerator = expressionGenerator;
        this.statementGenerator = statementGenerator;
    }

    public void generate(VariableDeclaration variableDeclaration) {
        Expression expression = variableDeclaration.getExpression();
        expression.accept(expressionGenerator);

        Assignment assignment = new Assignment(variableDeclaration.getName(), variableDeclaration.getExpression());
        assignment.accept(statementGenerator);
    }

}
