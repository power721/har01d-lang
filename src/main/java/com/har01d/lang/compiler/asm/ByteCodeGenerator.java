package com.har01d.lang.compiler.asm;

import java.util.Queue;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ByteCodeGenerator implements Opcodes {
    public byte[] generateByteCode(Queue<Instruction> instructions, String name) throws Exception {
        ClassWriter classWriter = new ClassWriter(0);
        MethodVisitor mv;
        classWriter.visit(52, ACC_PUBLIC + ACC_SUPER, name, null, "java/lang/Object", null);

        mv = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        long count = instructions.stream().filter(e -> e instanceof VariableDeclaration).count();
        int maxStack = 100;

        for (Instruction instruction : instructions) {
            instruction.apply(mv);
        }
        mv.visitInsn(RETURN);
        mv.visitMaxs(maxStack, (int) count);
        mv.visitEnd();

        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
