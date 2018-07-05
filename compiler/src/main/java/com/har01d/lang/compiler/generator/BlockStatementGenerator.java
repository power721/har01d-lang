package com.har01d.lang.compiler.generator;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Block;

public class BlockStatementGenerator {

    private final ClassWriter classWriter;
    private final MethodVisitor methodVisitor;

    public BlockStatementGenerator(ClassWriter classWriter, MethodVisitor methodVisitor) {
        this.classWriter = classWriter;
        this.methodVisitor = methodVisitor;
    }

    public void generate(Block block) {
        Scope scope = block.getScope();
        // TODO: new scope?
        StatementGenerator generator = new StatementGenerator(classWriter, methodVisitor, scope);
        block.getStatements().forEach(e -> e.accept(generator));

        MethodGenerator methodGenerator = new MethodGenerator(classWriter);
        block.getFunctions().forEach(e -> e.accept(methodGenerator));
    }

}
