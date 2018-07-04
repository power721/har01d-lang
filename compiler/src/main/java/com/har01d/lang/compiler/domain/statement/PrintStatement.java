package com.har01d.lang.compiler.domain.statement;

import java.util.List;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class PrintStatement implements Statement {

    private final List<Expression> expressions;

    public PrintStatement(List<Expression> expressions) {
        this.expressions = expressions;
    }

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
