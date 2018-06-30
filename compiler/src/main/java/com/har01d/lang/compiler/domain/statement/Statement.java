package com.har01d.lang.compiler.domain.statement;

import com.har01d.lang.compiler.generator.StatementGenerator;

public interface Statement {

    void accept(StatementGenerator generator);

}
