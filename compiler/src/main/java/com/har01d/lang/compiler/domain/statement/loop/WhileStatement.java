package com.har01d.lang.compiler.domain.statement.loop;

import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class WhileStatement implements Statement {

    private final Expression condition;
    private final Statement statement;
    private final boolean doWhile;

    public WhileStatement(Expression condition, Statement statement, boolean doWhile) {
        this.condition = condition;
        this.statement = statement;
        this.doWhile = doWhile;
    }

    public Expression getCondition() {
        return condition;
    }

    public Statement getStatement() {
        return statement;
    }

    public boolean isDoWhile() {
        return doWhile;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
