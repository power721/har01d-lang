package com.har01d.lang.compiler.domain.statement;

import com.har01d.lang.compiler.domain.statement.expression.Expression;

public class ValueDeclaration extends VariableDeclaration {

    public ValueDeclaration(String name, Expression expression) {
        super(name, expression);
    }

}
