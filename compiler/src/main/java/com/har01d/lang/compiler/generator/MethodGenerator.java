package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.ReturnStatement;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.EmptyExpression;
import java.util.stream.Collectors;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodGenerator {

    private final ClassWriter classWriter;

    public MethodGenerator(ClassWriter classWriter) {
        this.classWriter = classWriter;
    }

    public void generate(Function function) {
        String name = function.getName();
        int flag = Opcodes.ACC_PUBLIC;
        String descriptor = getDescriptor(function);
        Block block = (Block) function.getBlock();

        MethodVisitor mv = classWriter.visitMethod(flag, name, descriptor, null, null);
        mv.visitCode();
        StatementGenerator statementGenerator = new StatementGenerator(mv, block.getScope());
        block.accept(statementGenerator);

        appendReturnIfNotExists(function, block, statementGenerator);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();
    }

    private String getDescriptor(Function function) {
        String parameter = function.getParameters().stream().map(e -> e.getType().getDescriptor())
            .collect(Collectors.joining("", "(", ")"));
        return parameter + function.getReturnType().getDescriptor();
    }

    private void appendReturnIfNotExists(Function function, Block block, StatementGenerator statementGenerator) {
        boolean isReturnExist = false;
        if (!block.getStatements().isEmpty()) {
            Statement statement = block.getStatements().get(block.getStatements().size() - 1);
            isReturnExist = statement instanceof ReturnStatement;
        }
        if (!isReturnExist) {
            ReturnStatement returnStatement = new ReturnStatement(new EmptyExpression(function.getReturnType()));
            returnStatement.accept(statementGenerator);
        }
    }

}
