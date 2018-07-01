package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.statement.expression.Addition;
import com.har01d.lang.compiler.domain.statement.expression.Division;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
import com.har01d.lang.compiler.domain.statement.expression.Remainder;
import com.har01d.lang.compiler.domain.statement.expression.Subtraction;
import com.har01d.lang.compiler.domain.type.BultInType;
import com.har01d.lang.compiler.domain.type.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ArithmeticExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public ArithmeticExpressionGenerator(ExpressionGenerator expressionGenerator,
        MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(Addition expression) {
        if (expression.getType().equals(BultInType.STRING)) {
            generateStringAppend(expression);
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        expression.getRightExpression().accept(expressionGenerator);
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getAddOpcode());
    }

    public void generate(Subtraction expression) {
        expression.getLeftExpression().accept(expressionGenerator);
        expression.getRightExpression().accept(expressionGenerator);
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getSubtractOpcode());
    }

    public void generate(Multiplication expression) {
        expression.getLeftExpression().accept(expressionGenerator);
        expression.getRightExpression().accept(expressionGenerator);
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getMultiplyOpcode());
    }

    public void generate(Division expression) {
        expression.getLeftExpression().accept(expressionGenerator);
        expression.getRightExpression().accept(expressionGenerator);
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getDivideOpcode());
    }

    public void generate(Remainder expression) {
        expression.getLeftExpression().accept(expressionGenerator);
        expression.getRightExpression().accept(expressionGenerator);
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getRemainderOpcode());
    }

    private void generateStringAppend(Addition expression) {
        methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(Opcodes.DUP);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);
        Expression leftExpression = expression.getLeftExpression();
        leftExpression.accept(expressionGenerator);
        String descriptor = "(" + leftExpression.getType().getDescriptor() + ")Ljava/lang/StringBuilder;";
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false);

        Expression rightExpression = expression.getRightExpression();
        rightExpression.accept(expressionGenerator);
        descriptor = "(" + rightExpression.getType().getDescriptor() + ")Ljava/lang/StringBuilder;";
        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor, false);
        methodVisitor
            .visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;",
                false);
    }

}
