package com.har01d.lang.compiler.generator;

import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;

public class ReferenceExpressionGenerator {

    private final MethodVisitor methodVisitor;

    public ReferenceExpressionGenerator(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public void generate(LocalVariableReference reference) {
        int index = reference.getIndex();
        Type type = reference.getType();
        methodVisitor.visitVarInsn(type.getLoadVariableOpcode(), index);
    }

}
