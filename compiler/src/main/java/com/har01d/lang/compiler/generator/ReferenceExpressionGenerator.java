package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;
import org.objectweb.asm.MethodVisitor;

public class ReferenceExpressionGenerator {

    private final MethodVisitor methodVisitor;
    private final Scope scope;

    public ReferenceExpressionGenerator(MethodVisitor methodVisitor, Scope scope) {
        this.methodVisitor = methodVisitor;
        this.scope = scope;
    }

    public void generate(LocalVariableReference reference) {
        String varName = reference.geName();
        int index = scope.getLocalVariableIndex(varName);
        Type type = reference.getType();
        methodVisitor.visitVarInsn(type.getLoadVariableOpcode(), index);
    }

}

