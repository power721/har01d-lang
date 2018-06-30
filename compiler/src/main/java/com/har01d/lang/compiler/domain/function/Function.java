package com.har01d.lang.compiler.domain.function;

import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.MethodGenerator;
import java.util.List;

public class Function {

    private final FunctionSignature functionSignature;
    private final Statement block;

    public Function(FunctionSignature functionSignature, Statement block) {
        this.functionSignature = functionSignature;
        this.block = block;
    }

    public String getName() {
        return functionSignature.getName();
    }

    public List<FunctionParameter> getParameters() {
        return functionSignature.getParameters();
    }

    public Type getReturnType() {
        return functionSignature.getReturnType();
    }

    public Statement getBlock() {
        return block;
    }

    public void accept(MethodGenerator generator) {
        generator.generate(this);
    }

}
