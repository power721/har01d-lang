package com.har01d.lang.compiler.domain.statement;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class VariableDeclaration implements Statement {

    private final LocalVariable variable;
    private final Expression expression;

    public VariableDeclaration(LocalVariable variable, Expression expression) {
        this.variable = variable;
        this.expression = expression;
    }

    public LocalVariable getVariable() {
        return variable;
    }

    public String getName() {
        return variable.getName();
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
