package com.har01d.lang.compiler.asm;

import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.antlr4.Har01dParser;
import com.har01d.lang.compiler.domain.Variable;

import jdk.internal.org.objectweb.asm.Opcodes;

public class PrintVariable implements Instruction, Opcodes {
    private final Variable variable;

    public PrintVariable(Variable variable) {
        this.variable = variable;
    }

    @Override
    public void apply(MethodVisitor mv) {
        int type = variable.getType();
        int id = variable.getId();
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        if (type == Har01dParser.NUMBER) {
            mv.visitVarInsn(ILOAD, id);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        } else if (type == Har01dParser.BOOL) {
            mv.visitVarInsn(ILOAD, id);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Z)V", false);
        } else if (type == Har01dParser.STRING) {
            mv.visitVarInsn(ALOAD, id);
            mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(Ljava/lang/String;)V", false);
        }
    }
}
