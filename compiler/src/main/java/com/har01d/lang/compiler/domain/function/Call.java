package com.har01d.lang.compiler.domain.function;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import java.util.List;

public interface Call extends Expression {

    List<Argument> getArguments();

    String getIdentifier();
}
