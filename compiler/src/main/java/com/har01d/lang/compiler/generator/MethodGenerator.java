package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.function.Constructor;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.ReturnStatement;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.statement.expression.EmptyExpression;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.Type;
import java.util.stream.Collectors;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class MethodGenerator {

    private final ClassWriter classWriter;

    public MethodGenerator(ClassWriter classWriter) {
        this.classWriter = classWriter;
    }

    public void generate(Constructor constructor) {
        String descriptor = getDescriptor(constructor);
        Block block = (Block) constructor.getBlock();
        int flag = Opcodes.ACC_PUBLIC;

        MethodVisitor mv = classWriter.visitMethod(flag, "<init>", descriptor, null, null);
        mv.visitCode();
        StatementGenerator statementGenerator = new StatementGenerator(classWriter, mv, block.getScope());
        mv.visitVarInsn(Opcodes.ALOAD, 0);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
        block.accept(statementGenerator);

        appendReturnIfNotExists(constructor, block, statementGenerator);
        mv.visitMaxs(-1, -1);
        mv.visitEnd();
    }

    public void generate(Function function) {
        String name = function.getInternalName();
        String descriptor = getDescriptor(function);
        Block block = (Block) function.getBlock();
        int flag = function.getFunctionSignature().getFlag();

        MethodVisitor mv = classWriter.visitMethod(flag, name, descriptor, null, null);
        mv.visitCode();
        StatementGenerator statementGenerator = new StatementGenerator(classWriter, mv, block.getScope());
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
        Statement statement = null;
        if (!block.getStatements().isEmpty()) {
            statement = block.getStatements().get(block.getStatements().size() - 1);
            isReturnExist = statement instanceof ReturnStatement;
        }

        if (!isReturnExist) {
            ReturnStatement returnStatement = new ReturnStatement(new EmptyExpression(getType(statement)),
                                            function.getReturnType());
            returnStatement.accept(statementGenerator);
        }
    }

    private Type getType(Statement statement) {
        if (statement instanceof Expression) {
            return ((Expression) statement).getType();
        }
        return BuiltInType.VOID;
    }

}
