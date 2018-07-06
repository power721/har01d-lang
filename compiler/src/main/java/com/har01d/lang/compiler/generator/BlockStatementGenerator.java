package com.har01d.lang.compiler.generator;

import org.objectweb.asm.ClassWriter;

import com.har01d.lang.compiler.domain.statement.Block;

public class BlockStatementGenerator {

    private final ClassWriter classWriter;
    private final StatementGenerator statementGenerator;

    public BlockStatementGenerator(ClassWriter classWriter, StatementGenerator statementGenerator) {
        this.classWriter = classWriter;
        this.statementGenerator = statementGenerator;
    }

    public void generate(Block block) {
        block.getStatements().forEach(e -> e.accept(statementGenerator));

        MethodGenerator methodGenerator = new MethodGenerator(classWriter);
        block.getFunctions().forEach(e -> e.accept(methodGenerator));
    }

}
