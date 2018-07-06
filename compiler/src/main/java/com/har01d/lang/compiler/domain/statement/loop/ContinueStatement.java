package com.har01d.lang.compiler.domain.statement.loop;

import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class ContinueStatement implements Statement {

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
