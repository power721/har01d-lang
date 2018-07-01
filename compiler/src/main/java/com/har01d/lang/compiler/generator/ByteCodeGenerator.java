package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ByteCodeGenerator implements Opcodes {

    public byte[] generateByteCode(CompilationUnit compilationUnit, String name) throws Exception {
        ClassWriter classWriter = new ClassWriter(0);
        MethodVisitor mv;
        classWriter.visit(52, ACC_PUBLIC + ACC_SUPER, name, null, "java/lang/Object", null);

        MethodGenerator methodGenerator = new MethodGenerator(classWriter);
        compilationUnit.getFunctions().forEach(e -> e.accept(methodGenerator));

        mv = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        long count = compilationUnit.getStatements().stream().filter(e -> e instanceof VariableDeclaration).count();
        int maxStack = 100;

        StatementGenerator statementGenerator = new StatementGenerator(mv, compilationUnit.getScope());
        for (Statement statement : compilationUnit.getStatements()) {
            statement.accept(statementGenerator);
        }

        mv.visitInsn(RETURN);
        mv.visitMaxs(maxStack, (int) count);
        mv.visitEnd();

        classWriter.visitEnd();

        byte[] bytecode = classWriter.toByteArray();
        ClassReader cr = new ClassReader(bytecode);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cr.accept(cw, ClassReader.SKIP_FRAMES);
        return cw.toByteArray();
    }

}
