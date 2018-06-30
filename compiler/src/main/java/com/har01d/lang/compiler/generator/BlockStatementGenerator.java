package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Block;
import org.objectweb.asm.MethodVisitor;

public class BlockStatementGenerator {

    private final MethodVisitor methodVisitor;

    public BlockStatementGenerator(MethodVisitor methodVisitor) {
        this.methodVisitor = methodVisitor;
    }

    public void generate(Block block) {
        Scope scope = block.getScope();
        // TODO: new scope?
        StatementGenerator generator = new StatementGenerator(methodVisitor, scope);
        block.getStatements().forEach(e -> e.accept(generator));
    }

}
