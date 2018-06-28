package com.har01d.lang.compiler.asm;

import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.antlr4.Har01dParser;
import com.har01d.lang.compiler.domain.Variable;

import jdk.internal.org.objectweb.asm.Opcodes;

public class VariableDeclaration implements Instruction, Opcodes {
    private final Variable variable;

    public VariableDeclaration(Variable variable) {
        this.variable = variable;
    }

    @Override
    public void apply(MethodVisitor mv) {
        int type = variable.getType();
        if (type == Har01dParser.NUMBER) {
            int val = Integer.valueOf(variable.getValue());
            mv.visitIntInsn(BIPUSH, val);
            mv.visitVarInsn(ISTORE, variable.getId());
        } else if (type == Har01dParser.STRING) {
            mv.visitLdcInsn(variable.getValue());
            mv.visitVarInsn(ASTORE, variable.getId());
        }
    }
}
