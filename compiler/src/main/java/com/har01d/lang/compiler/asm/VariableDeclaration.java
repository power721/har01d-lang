package com.har01d.lang.compiler.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.Variable;

public class VariableDeclaration implements Instruction, Opcodes {
    private final Variable variable;

    public VariableDeclaration(Variable variable) {
        this.variable = variable;
    }

    @Override
    public void apply(MethodVisitor mv) {
        int type = variable.getType();
        int id = variable.getId();
        if (type == Har01dParser.NUMBER) {
            int val = Integer.valueOf(variable.getValue());
            mv.visitIntInsn(BIPUSH, val);
            mv.visitVarInsn(ISTORE, id);
        } else if (type == Har01dParser.BOOL) {
            boolean val = Boolean.valueOf(variable.getValue());
            mv.visitInsn(val ? ICONST_1 : ICONST_0);
            mv.visitVarInsn(ISTORE, id);
        } else if (type == Har01dParser.STRING) {
            mv.visitLdcInsn(variable.getValue());
            mv.visitVarInsn(ASTORE, id);
        }
    }
}
