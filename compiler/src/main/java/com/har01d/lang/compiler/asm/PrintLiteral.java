package com.har01d.lang.compiler.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.Literal;

public class PrintLiteral implements Instruction, Opcodes {
    private final Literal literal;

    public PrintLiteral(Literal literal) {
        this.literal = literal;
    }

    @Override
    public void apply(MethodVisitor mv) {
        int type = literal.getType();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        if (type == Har01dParser.NUMBER) {
            int val = Integer.valueOf(literal.getValue());
            mv.visitIntInsn(BIPUSH, val);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        } else if (type == Har01dParser.BOOL) {
            boolean val = Boolean.valueOf(literal.getValue());
            mv.visitInsn(val ? ICONST_1 : ICONST_0);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Z)V", false);
        } else if (type == Har01dParser.STRING) {
            mv.visitLdcInsn(literal.getValue());
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
    }
}
