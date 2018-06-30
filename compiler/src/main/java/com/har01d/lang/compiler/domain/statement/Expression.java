package com.har01d.lang.compiler.domain.statement;

import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;

public interface Expression extends Statement {

    Type getType();

    void accept(ExpressionGenerator generator);

}
