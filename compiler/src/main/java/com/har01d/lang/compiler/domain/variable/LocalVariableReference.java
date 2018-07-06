package com.har01d.lang.compiler.domain.variable;

import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;
import com.har01d.lang.compiler.generator.StatementGenerator;

public class LocalVariableReference implements Reference {

    private final LocalVariable variable;

    public LocalVariableReference(LocalVariable variable) {
        this.variable = variable;
    }

    @Override
    public String geName() {
        return variable.getName();
    }

    @Override
    public Type getType() {
        return variable.getType();
    }

    public int getIndex() {
        return variable.getIndex();
    }

    @Override
    public void accept(ExpressionGenerator generator) {
        generator.generate(this);
    }

    @Override
    public void accept(StatementGenerator generator) {
        generator.generate(this);
    }

}
