package com.har01d.lang.compiler.generator;

import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.domain.function.FunctionParameter;
import com.har01d.lang.compiler.domain.type.Type;

public class ParameterExpressionGenerator {

    private final MethodVisitor methodVisitor;

    public ParameterExpressionGenerator(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public void generate(FunctionParameter parameter) {
        Type type = parameter.getType();
        int index = parameter.getIndex();
        methodVisitor.visitVarInsn(type.getLoadVariableOpcode(), index);
    }

}
