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
import com.har01d.lang.compiler.util.TypeUtil;

public class RelationalExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public RelationalExpressionGenerator(ExpressionGenerator expressionGenerator, MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    private static Type getCommonType(Expression leftExpression, Expression rightExpression) {
        if (leftExpression.getType() == BuiltInType.STRING || rightExpression.getType() == BuiltInType.STRING) {
            return BuiltInType.STRING;
        }
        if (leftExpression.getType().equals(ClassType.BIG_INTEGER)
                                        || rightExpression.getType().equals(ClassType.BIG_INTEGER)) {
            return ClassType.BIG_INTEGER;
        }
        if (leftExpression.getType() == BuiltInType.DOUBLE || rightExpression.getType() == BuiltInType.DOUBLE) {
            return BuiltInType.DOUBLE;
        }
        if (leftExpression.getType() == BuiltInType.LONG || rightExpression.getType() == BuiltInType.LONG) {
            return BuiltInType.LONG;
        }
        return leftExpression.getType();
    }

    public void generate(RelationalExpression expression) {
        if (isPrimitiveComparison(expression)) {
            Type type = getCommonType(expression.getLeftExpression(), expression.getRightExpression());
            expression.getLeftExpression().accept(expressionGenerator);
            castIfRequired(expression.getLeftExpression(), type);
            expression.getRightExpression().accept(expressionGenerator);
            castIfRequired(expression.getRightExpression(), type);
            if (type == BuiltInType.DOUBLE) {
                methodVisitor.visitInsn(Opcodes.DCMPG);
            } else if (type == BuiltInType.LONG) {
                methodVisitor.visitInsn(Opcodes.LCMP);
            } else {
                methodVisitor.visitInsn(Opcodes.ISUB);
            }
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
        return TypeUtil.isPrimitive(leftExpression.getType()) && TypeUtil.isPrimitive(rightExpression.getType());
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
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, getClassType(leftExpression.getType()), "equals",
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
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, getClassType(leftExpression.getType()),
                                                "compareTo", descriptor, false);
                break;
        }
    }

    private void generateSubExpression(Expression expression, Type type) {
        if (type.equals(ClassType.BIG_INTEGER) && !expression.getType().equals(ClassType.BIG_INTEGER)) {
            TypeUtil.cast2BigInteger(methodVisitor, expressionGenerator, expression);
        } else {
            expression.accept(expressionGenerator);
            castIfRequired(expression, type);
        }
    }

    private String getClassType(Type type) {
        if (type.equals(BuiltInType.INT)) {
            return ClassType.Integer().getInternalName();
        }
        if (type.equals(BuiltInType.LONG)) {
            return ClassType.Long().getInternalName();
        }
        if (type.equals(BuiltInType.BOOLEAN)) {
            return ClassType.Boolean().getInternalName();
        }
        if (type.equals(BuiltInType.DOUBLE)) {
            return ClassType.Double().getInternalName();
        }
        return type.getInternalName();
    }

    private void castIfRequired(Expression expression, Type type) {
        if (expression.getType().equals(type)) {
            return;
        }

        if (expression.getType().equals(BuiltInType.INT)) {
            if (type.equals(BuiltInType.DOUBLE)) {
                methodVisitor.visitInsn(Opcodes.I2D);
            } else if (type.equals(BuiltInType.LONG)) {
                methodVisitor.visitInsn(Opcodes.I2L);
            } else if (!TypeUtil.isPrimitive(type)) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Integer;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", descriptor, false);
            }
        }

        if (expression.getType().equals(BuiltInType.LONG)) {
            if (type.equals(BuiltInType.DOUBLE)) {
                methodVisitor.visitInsn(Opcodes.L2D);
            } else if (!TypeUtil.isPrimitive(type)) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Long;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", descriptor, false);
            }
        }

        if (expression.getType().equals(BuiltInType.DOUBLE)) {
            if (!TypeUtil.isPrimitive(type)) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Double;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", descriptor, false);
            }
        }

    }

}
