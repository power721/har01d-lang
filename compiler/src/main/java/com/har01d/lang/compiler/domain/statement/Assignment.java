package com.har01d.lang.compiler.domain.statement;

import com.har01d.lang.compiler.generator.StatementGenerator;

public class Assignment implements Statement {

    private final String varName;
    private final Expression expression;

    public Assignment(String varName, Expression expression) {
        this.varName = varName;
        this.expression = expression;
    }

    public String getVarName() {
        return varName;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
