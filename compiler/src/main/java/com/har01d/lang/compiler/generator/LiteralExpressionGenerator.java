package com.har01d.lang.compiler.generator;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;

public class LiteralExpressionGenerator {

    private final MethodVisitor methodVisitor;

    public LiteralExpressionGenerator(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public void generate(Literal literal) {
        Type type = literal.getType();
        String value = literal.getValue();
        if (type == ClassType.BIG_INTEGER) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, type.getInternalName());
            methodVisitor.visitInsn(Opcodes.DUP);
            methodVisitor.visitLdcInsn(value);
            methodVisitor
                .visitMethodInsn(Opcodes.INVOKESPECIAL, type.getInternalName(), "<init>", "(Ljava/lang/String;)V",
                    false);
        } else {
            methodVisitor.visitLdcInsn(TypeResolver.getValue(type, value));
        }
    }

}
