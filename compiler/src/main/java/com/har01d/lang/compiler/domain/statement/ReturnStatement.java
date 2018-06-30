package com.har01d.lang.compiler.domain.statement;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class ReturnStatement implements Statement {

    private final Expression expression;

    public ReturnStatement(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
