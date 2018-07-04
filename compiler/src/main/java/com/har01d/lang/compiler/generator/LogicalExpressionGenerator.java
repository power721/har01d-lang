package com.har01d.lang.compiler.generator;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.LogicalOperator;
import com.har01d.lang.compiler.domain.statement.expression.LogicalExpression;

public class LogicalExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public LogicalExpressionGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(LogicalExpression expression) {
        LogicalOperator operator = expression.getOperator();
        Label trueLabel = new Label();
        Label falseLabel = new Label();
        Label endLabel = new Label();

        switch (operator) {
            case NOT:
                expression.getLeftExpression().accept(expressionGenerator);
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, trueLabel);
                methodVisitor.visitInsn(Opcodes.ICONST_0);
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
                methodVisitor.visitLabel(trueLabel);
                methodVisitor.visitInsn(Opcodes.ICONST_1);
                methodVisitor.visitLabel(endLabel);
                break;
            case OR:
                expression.getLeftExpression().accept(expressionGenerator);
                methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel);
                expression.getRightExpression().accept(expressionGenerator);
                methodVisitor.visitJumpInsn(Opcodes.IFNE, trueLabel);
                methodVisitor.visitInsn(Opcodes.ICONST_0);
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
                methodVisitor.visitLabel(trueLabel);
                methodVisitor.visitInsn(Opcodes.ICONST_1);
                methodVisitor.visitLabel(endLabel);
                break;
            case AND:
                expression.getLeftExpression().accept(expressionGenerator);
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, falseLabel);
                expression.getRightExpression().accept(expressionGenerator);
                methodVisitor.visitJumpInsn(Opcodes.IFEQ, falseLabel);
                methodVisitor.visitInsn(Opcodes.ICONST_1);
                methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
                methodVisitor.visitLabel(falseLabel);
                methodVisitor.visitInsn(Opcodes.ICONST_0);
                methodVisitor.visitLabel(endLabel);
                break;
        }
    }

}
