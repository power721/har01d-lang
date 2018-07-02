package com.har01d.lang.compiler.generator;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.CompareSign;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;

public class RelationalExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public RelationalExpressionGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    private static Type getCommonType(Expression leftExpression, Expression rightExpression) {
        if (rightExpression.getType() == ClassType.BIGINTEGER) {
            return ClassType.BIGINTEGER;
        }
        if (rightExpression.getType() == BuiltInType.STRING) {
            return BuiltInType.STRING;
        }
        if (rightExpression.getType() == BuiltInType.DOUBLE) {
            return BuiltInType.DOUBLE;
        }
        return leftExpression.getType();
    }

    public void generate(RelationalExpression expression) {
        if (isPrimitiveComparison(expression)) {
            expression.getLeftExpression().accept(expressionGenerator);
            expression.getRightExpression().accept(expressionGenerator);
            methodVisitor.visitInsn(Opcodes.ISUB);
        } else {
            generate4Object(expression);
        }

        Label trueLabel = new Label();
        Label endLabel = new Label();
        methodVisitor.visitJumpInsn(expression.getCompareSign().getOpcode(), trueLabel);
        methodVisitor.visitInsn(Opcodes.ICONST_0);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLabel);
        methodVisitor.visitLabel(trueLabel);
        methodVisitor.visitInsn(Opcodes.ICONST_1);
        methodVisitor.visitLabel(endLabel);
    }

    private boolean isPrimitiveComparison(RelationalExpression expression) {
        Expression leftExpression = expression.getLeftExpression();
        Expression rightExpression = expression.getRightExpression();
        return leftExpression.getType().getTypeClass().isPrimitive() && rightExpression.getType().getTypeClass()
            .isPrimitive();
    }

    public void generate4Object(RelationalExpression expression) {
        CompareSign compareSign = expression.getCompareSign();
        Expression leftExpression = expression.getLeftExpression();
        Expression rightExpression = expression.getRightExpression();
        Type type = getCommonType(expression.getLeftExpression(), expression.getRightExpression());

        switch (compareSign) {
            case EQUAL:
            case NOT_EQUAL:
                generateSubExpression(leftExpression, type);
                generateSubExpression(rightExpression, type);
                methodVisitor
                    .visitMethodInsn(Opcodes.INVOKEVIRTUAL, leftExpression.getType().getInternalName(), "equals",
                        "(Ljava/lang/Object;)Z", false);
                methodVisitor.visitInsn(Opcodes.ICONST_1);
                methodVisitor.visitInsn(Opcodes.IXOR);
                break;
            case LESS:
            case GREATER:
            case LESS_OR_EQUAL:
            case GRATER_OR_EQUAL:
                generateSubExpression(leftExpression, type);
                generateSubExpression(rightExpression, type);
                String descriptor = "(" + leftExpression.getType().getDescriptor() + ")I";
                methodVisitor
                    .visitMethodInsn(Opcodes.INVOKEVIRTUAL, leftExpression.getType().getInternalName(), "compareTo",
                        descriptor, false);
                break;
        }
    }

    private void generateSubExpression(Expression expression, Type type) {
        if (type == ClassType.BIGINTEGER && expression.getType() != ClassType.BIGINTEGER) {
            methodVisitor.visitTypeInsn(Opcodes.NEW, type.getInternalName());
            methodVisitor.visitInsn(Opcodes.DUP);
            expression.accept(expressionGenerator);
            if (expression.getType() != BuiltInType.STRING) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/String;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", descriptor, false);
            }
            methodVisitor
                .visitMethodInsn(Opcodes.INVOKESPECIAL, type.getInternalName(), "<init>", "(Ljava/lang/String;)V",
                    false);
        } else {
            expression.accept(expressionGenerator);
            castIfRequired(expression, type);
        }
    }

    private void castIfRequired(Expression expression, Type type) {
        if (expression.getType().equals(type)) {
            return;
        }

        if (expression.getType().equals(BuiltInType.INT)) {
            if (type.equals(BuiltInType.DOUBLE)) {
                methodVisitor.visitInsn(Opcodes.I2D);
            }
            if (type.equals(BuiltInType.LONG)) {
                methodVisitor.visitInsn(Opcodes.I2L);
            }
        }
    }

}
