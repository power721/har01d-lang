package com.har01d.lang.compiler.domain.function;

import java.util.List;

import com.har01d.lang.compiler.domain.statement.expression.Expression;

public interface Call extends Expression {

    List<Argument> getArguments();

    String getIdentifier();

}
