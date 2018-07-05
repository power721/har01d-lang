package com.har01d.lang.compiler.generator;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.ClassDeclaration;
import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.VariableDeclaration;

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

        StatementGenerator statementGenerator = new StatementGenerator(classWriter, mv, compilationUnit.getScope());
        for (Statement statement : compilationUnit.getStatements()) {
            statement.accept(statementGenerator);
        }

        mv.visitInsn(RETURN);
        mv.visitMaxs(maxStack, (int) count);
        mv.visitEnd();

        classWriter.visitEnd();

        ClassReader cr = new ClassReader(classWriter.toByteArray());
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cr.accept(cw, ClassReader.SKIP_FRAMES);
        return cw.toByteArray();
    }

    public byte[] generateByteCode(ClassDeclaration classDeclaration) {
        ClassWriter classWriter = new ClassWriter(0);
        MethodVisitor mv;
        classWriter.visit(52, ACC_PUBLIC + ACC_SUPER, classDeclaration.getName(), null, "java/lang/Object", null);

        MethodGenerator methodGenerator = new MethodGenerator(classWriter);
        classDeclaration.getMethods().forEach(e -> e.accept(methodGenerator));

        //        mv = classWriter.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        //        long count = compilationUnit.getStatements().stream().filter(e -> e instanceof VariableDeclaration).count();
        //        int maxStack = 100;
        //
        //        StatementGenerator statementGenerator = new StatementGenerator(mv, compilationUnit.getScope());
        //        for (Statement statement : compilationUnit.getStatements()) {
        //            statement.accept(statementGenerator);
        //        }
        //
        //        mv.visitInsn(RETURN);
        //        mv.visitMaxs(maxStack, (int) count);
        //        mv.visitEnd();

        classWriter.visitEnd();

        ClassReader cr = new ClassReader(classWriter.toByteArray());
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
        cr.accept(cw, ClassReader.SKIP_FRAMES);
        return cw.toByteArray();
    }

}
