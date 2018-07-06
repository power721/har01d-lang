package com.har01d.lang.compiler.domain.function;

import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.generator.MethodGenerator;

public class Constructor extends Function {

    public Constructor(FunctionSignature functionSignature, Statement block) {
        super(functionSignature, block);
    }

    public void accept(MethodGenerator generator) {
        generator.generate(this);
    }

}
