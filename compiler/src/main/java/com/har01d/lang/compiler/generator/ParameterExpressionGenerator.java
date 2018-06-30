package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionParameter;
import com.har01d.lang.compiler.domain.type.Type;
import org.objectweb.asm.MethodVisitor;

public class ParameterExpressionGenerator {

    private final MethodVisitor methodVisitor;
    private final Scope scope;

    public ParameterExpressionGenerator(MethodVisitor methodVisitor, Scope scope) {
        this.methodVisitor = methodVisitor;
        this.scope = scope;
    }

    public void generate(FunctionParameter parameter) {
        Type type = parameter.getType();
        int index = scope.getLocalVariableIndex(parameter.getName());
        methodVisitor.visitVarInsn(type.getLoadVariableOpcode(), index);
    }

}
