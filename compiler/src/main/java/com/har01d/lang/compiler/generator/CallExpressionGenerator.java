package com.har01d.lang.compiler.generator;

import java.util.stream.Collectors;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionCall;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.ClassType;

public class CallExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;
    private final Scope scope;

    public CallExpressionGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor, Scope scope) {
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

        FunctionSignature signature = functionCall.getSignature();
        functionCall.getArguments().forEach(e -> e.accept(expressionGenerator));
        for (int i = functionCall.getArguments().size(); i < signature.getParameters().size(); ++i) {
            Expression defaultValue = signature.getParameters().get(i).getDefaultValue()
                                            .orElseThrow(() -> new RuntimeException(""));
            defaultValue.accept(expressionGenerator);
        }

        String name = functionCall.getIdentifier();
        String descriptor = getDescriptor(signature);

        // TODO: check static method
        if (scope.isClassDeclaration()) {
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, name, descriptor, false);
        } else {
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, owner, name, descriptor, false);
        }
    }

    private String getDescriptor(FunctionSignature signature) {
        String parameter = signature.getParameters().stream().map(e -> e.getType().getDescriptor())
                                        .collect(Collectors.joining("", "(", ")"));
        return parameter + signature.getReturnType().getDescriptor();
    }

}
