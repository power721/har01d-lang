package com.har01d.lang.compiler.domain.statement.loop;

import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class RangedForStatement implements Statement {
    private final Statement iterator;
    private final LocalVariable variable;
    private final Expression endExpression;
    private final int step;
    private final Statement statement;

    public RangedForStatement(Statement iterator, LocalVariable variable, Expression endExpression, int step,
                                    Statement statement) {
        this.iterator = iterator;
        this.variable = variable;
        this.endExpression = endExpression;
        this.step = step;
        this.statement = statement;
    }

    public Statement getIterator() {
        return iterator;
    }

    public LocalVariable getVariable() {
        return variable;
    }

    public Expression getEndExpression() {
        return endExpression;
    }

    public int getStep() {
        return step;
    }

    public Statement getStatement() {
        return statement;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
