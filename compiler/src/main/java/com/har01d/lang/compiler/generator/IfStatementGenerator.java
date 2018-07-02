package com.har01d.lang.compiler.generator;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.statement.IfStatement;

public class IfStatementGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final StatementGenerator statementGenerator;
    private final MethodVisitor methodVisitor;

    public IfStatementGenerator(ExpressionGenerator expressionGenerator, StatementGenerator statementGenerator,
                                    MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.statementGenerator = statementGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(IfStatement statement) {
        statement.getCondition().accept(expressionGenerator);

        Label trueLabel = new Label();
        Label endLabel = new Label();
        methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel);
        if (statement.getFalseStatement() != null) {
            statement.getFalseStatement().accept(statementGenerator);
        }
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
        methodVisitor.visitLabel(trueLabel);
        statement.getTrueStatement().accept(statementGenerator);
        methodVisitor.visitLabel(endLabel);
    }

}
