package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionCall;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.type.ClassType;
import java.util.stream.Collectors;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class CallExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;
    private final Scope scope;

    public CallExpressionGenerator(ExpressionGenerator expressionGenerator,
        MethodVisitor methodVisitor, Scope scope) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
        this.scope = scope;
    }

    public void generate(FunctionCall functionCall) {
        String owner;
        if (functionCall.getOwner() == null) {
            ClassType thisType = new ClassType(scope.getClassName());
            owner = thisType.getInternalName();
        } else {
            functionCall.getOwner().accept(expressionGenerator);
            owner = functionCall.getOwner().getType().getInternalName();
        }
        functionCall.getArguments().forEach(e -> e.accept(expressionGenerator));
        String name = functionCall.getIdentifier();
        FunctionSignature signature = functionCall.getSignature();
        String descriptor = getDescriptor(signature);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, descriptor, false);
    }

    private String getDescriptor(FunctionSignature signature) {
        String parameter = signature.getParameters().stream().map(e -> e.getType().getDescriptor())
            .collect(Collectors.joining("", "(", ")"));
        return parameter + signature.getReturnType().getDescriptor();
    }

}
