package com.har01d.lang.compiler.generator;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.CompareSign;
import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;
import com.har01d.lang.compiler.domain.statement.loop.BreakStatement;
import com.har01d.lang.compiler.domain.statement.loop.ContinueStatement;
import com.har01d.lang.compiler.domain.statement.loop.RangedForStatement;
import com.har01d.lang.compiler.domain.statement.loop.WhileStatement;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;

public class LoopStatementGenerator {

    private final ClassWriter classWriter;
    private final MethodVisitor methodVisitor;
    private final StatementGenerator statementGenerator;

    private Label loopLabel;
    private Label continueLabel;
    private Label endLabel;

    public LoopStatementGenerator(ClassWriter classWriter, MethodVisitor methodVisitor,
                                    StatementGenerator statementGenerator) {
        this.classWriter = classWriter;
        this.methodVisitor = methodVisitor;
        this.statementGenerator = statementGenerator;
    }

    public void generate(RangedForStatement forStatement) {
        forStatement.getIterator().accept(statementGenerator);
        loopLabel = new Label();
        continueLabel = new Label();
        endLabel = new Label();
        int step = forStatement.getStep();

        LocalVariableReference reference = new LocalVariableReference(forStatement.getVariable());
        RelationalExpression conditionalExpression;
        if (step > 0) {
            conditionalExpression = new RelationalExpression(reference, forStatement.getEndExpression(),
                                            CompareSign.LESS_OR_EQUAL);
        } else {
            conditionalExpression = new RelationalExpression(reference, forStatement.getEndExpression(),
                                            CompareSign.GRATER_OR_EQUAL);
        }

        conditionalExpression.accept(statementGenerator);
        methodVisitor.visitJumpInsn(Opcodes.IFNE, loopLabel);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);

        methodVisitor.visitLabel(loopLabel);
        forStatement.getStatement().accept(statementGenerator);
        methodVisitor.visitLabel(continueLabel);
        methodVisitor.visitIincInsn(forStatement.getVariable().getIndex(), step);
        conditionalExpression.accept(statementGenerator);
        methodVisitor.visitJumpInsn(Opcodes.IFNE, loopLabel);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);

        methodVisitor.visitLabel(endLabel);
    }

    public void generate(WhileStatement whileStatement) {
        whileStatement.getCondition().accept(statementGenerator);
        loopLabel = new Label();
        continueLabel = new Label();
        endLabel = new Label();

        methodVisitor.visitJumpInsn(Opcodes.IFNE, loopLabel);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);

        methodVisitor.visitLabel(loopLabel);
        whileStatement.getStatement().accept(statementGenerator);
        methodVisitor.visitLabel(continueLabel);
        whileStatement.getCondition().accept(statementGenerator);
        methodVisitor.visitJumpInsn(Opcodes.IFNE, loopLabel);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);

        methodVisitor.visitLabel(endLabel);
    }

    public void generate(BreakStatement breakStatement) {
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
    }

    public void generate(ContinueStatement continueStatement) {
        methodVisitor.visitJumpInsn(Opcodes.GOTO, continueLabel);
    }

}
