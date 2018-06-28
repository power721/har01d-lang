package com.har01d.lang.compiler.asm;

import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.antlr4.Har01dParser;
import com.har01d.lang.compiler.domain.Value;

import jdk.internal.org.objectweb.asm.Opcodes;

public class PrintValue implements Instruction, Opcodes {
    private final Value value;

    public PrintValue(Value value) {
        this.value = value;
    }

    @Override
    public void apply(MethodVisitor mv) {
        int type = value.getType();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        if (type == Har01dParser.NUMBER) {
            int val = Integer.valueOf(value.getValue());
            mv.visitIntInsn(BIPUSH, val);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        } else if (type == Har01dParser.STRING) {
            mv.visitLdcInsn(value.getValue());
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
    }
}
