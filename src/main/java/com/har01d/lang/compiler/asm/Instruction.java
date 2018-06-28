package com.har01d.lang.compiler.asm;

import org.objectweb.asm.MethodVisitor;

public interface Instruction {
    void apply(MethodVisitor mv);
}
