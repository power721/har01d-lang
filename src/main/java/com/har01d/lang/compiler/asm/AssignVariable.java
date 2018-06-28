package com.har01d.lang.compiler.asm;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.antlr4.Har01dParser;
import com.har01d.lang.compiler.domain.Variable;

public class AssignVariable implements Instruction, Opcodes {
    private final Variable variable;
    private final String value;

    public AssignVariable(Variable variable, String value) {
        this.variable = variable;
        this.value = value;
    }

    @Override
    public void apply(MethodVisitor mv) {
        int type = variable.getType();
        int id = variable.getId();
        if (type == Har01dParser.NUMBER) {
            int val = Integer.valueOf(value);
            mv.visitIntInsn(BIPUSH, val);
            mv.visitVarInsn(ISTORE, id);
        } else if (type == Har01dParser.STRING) {
            mv.visitLdcInsn(value);
            mv.visitVarInsn(ASTORE, id);
        }
    }
}
