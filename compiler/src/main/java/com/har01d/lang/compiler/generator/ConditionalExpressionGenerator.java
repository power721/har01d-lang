package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ConditionalExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public ConditionalExpressionGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(RelationalExpression expression) {
        expression.getLeftExpression().accept(expressionGenerator);
        expression.getRightExpression().accept(expressionGenerator);
        methodVisitor.visitInsn(Opcodes.ISUB);
        Label trueLabel = new Label();
        Label endLabel = new Label();
        methodVisitor.visitJumpInsn(expression.getCompareSign().getOpcode(), trueLabel);
        methodVisitor.visitInsn(Opcodes.ICONST_0);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
        methodVisitor.visitLabel(trueLabel);
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitLabel(endLabel);
    }

}
