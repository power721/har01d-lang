package com.har01d.lang.compiler.domain.statement.loop;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class BreakStatement implements Statement {
    private final Scope scope;

    public BreakStatement(Scope scope) {
        this.scope = scope;
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
