package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;
import org.objectweb.asm.MethodVisitor;

public class LiteralExpressionGenerator {

    private final MethodVisitor methodVisitor;

    public LiteralExpressionGenerator(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public void generate(Literal literal) {
        Type type = literal.getType();
        String value = literal.getValue();
        methodVisitor.visitLdcInsn(TypeResolver.getValue(type, value));
    }

}
