package com.har01d.lang.compiler.util;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.generator.ExpressionGenerator;

public final class TypeUtil {

    public static boolean isPrimitive(Type type) {
        if (type == BuiltInType.BOOLEAN) {
            return true;
        }
        if (type == BuiltInType.CHAR) {
            return true;
        }
        if (type == BuiltInType.BYTE) {
            return true;
        }
        if (type == BuiltInType.SHORT) {
            return true;
        }
        if (type == BuiltInType.INT) {
            return true;
        }
        if (type == BuiltInType.LONG) {
            return true;
        }
        if (type == BuiltInType.FLOAT) {
            return true;
        }
        if (type == BuiltInType.DOUBLE) {
            return true;
        }
        return false;
    }

    public static void castIfRequired(MethodVisitor methodVisitor, Expression expression, Type type) {
        if (expression.getType().equals(type)) {
            return;
        }

        if (expression.getType().equals(BuiltInType.INT)) {
            if (type.equals(BuiltInType.DOUBLE)) {
                methodVisitor.visitInsn(Opcodes.I2D);
            } else if (type.equals(BuiltInType.LONG)) {
                methodVisitor.visitInsn(Opcodes.I2L);
            } else if (type.equals(ClassType.Integer())) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Integer;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", descriptor, false);
            } else if (type.equals(ClassType.Double())) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Double;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", descriptor, false);
            } else if (type.equals(ClassType.Long())) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Long;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", descriptor, false);
            }
        }

        if (expression.getType().equals(BuiltInType.LONG)) {
            if (type.equals(BuiltInType.DOUBLE)) {
                methodVisitor.visitInsn(Opcodes.L2D);
            } else if (type.equals(ClassType.Long())) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Long;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", descriptor, false);
            } else if (type.equals(ClassType.Double())) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Double;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", descriptor, false);
            }
        }

        if (expression.getType().equals(BuiltInType.DOUBLE)) {
            if (type.equals(ClassType.Double())) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/Double;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", descriptor, false);
            }
        }

        if (expression.getType().equals(ClassType.BIG_INTEGER)) {
            if (type.equals(BuiltInType.DOUBLE)) {
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/math/BigInteger", "doubleValue", "()D",
                                                false);
            }
        }
    }

    public static void cast2BigInteger(MethodVisitor methodVisitor, ExpressionGenerator expressionGenerator,
                                    Expression expression) {
        methodVisitor.visitTypeInsn(Opcodes.NEW, "java/math/BigInteger");
        methodVisitor.visitInsn(Opcodes.DUP);
        expression.accept(expressionGenerator);
        if (expression.getType() != BuiltInType.STRING) {
            String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/String;";
            methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", descriptor, false);
        }
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/math/BigInteger", "<init>", "(Ljava/lang/String;)V",
                                        false);
    }

}
