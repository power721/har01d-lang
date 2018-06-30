package com.har01d.lang.compiler.domain.statement;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class VariableDeclaration implements Statement {

    private final String name;
    private final Expression expression;

    public VariableDeclaration(String name, Expression expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
