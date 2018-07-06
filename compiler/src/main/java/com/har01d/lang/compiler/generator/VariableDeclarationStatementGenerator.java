package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;
import com.har01d.lang.compiler.domain.statement.expression.Expression;

public class VariableDeclarationStatementGenerator {

    private final StatementGenerator statementGenerator;

    public VariableDeclarationStatementGenerator(StatementGenerator statementGenerator) {
        this.statementGenerator = statementGenerator;
    }

    public void generate(VariableDeclaration variableDeclaration) {
        Expression expression = variableDeclaration.getExpression();
        if (expression != null) {
            Assignment assignment = new Assignment(variableDeclaration.getVariable(), expression);
            assignment.accept(statementGenerator);
        }
    }

}
