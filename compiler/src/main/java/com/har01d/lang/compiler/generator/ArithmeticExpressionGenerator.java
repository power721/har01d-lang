package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.statement.expression.Addition;
import com.har01d.lang.compiler.domain.statement.expression.ArithmeticExpression;
import com.har01d.lang.compiler.domain.statement.expression.Division;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
import com.har01d.lang.compiler.domain.statement.expression.Power;
import com.har01d.lang.compiler.domain.statement.expression.Remainder;
import com.har01d.lang.compiler.domain.statement.expression.Subtraction;
import com.har01d.lang.compiler.domain.type.BultInType;
import com.har01d.lang.compiler.domain.type.ClassType;
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

        if (expression.getType() == ClassType.BIGINTEGER) {
            generateBigInteger(expression, "add");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        castIfRequired(expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        castIfRequired(expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getAddOpcode());
    }

    public void generate(Subtraction expression) {
        if (expression.getType() == ClassType.BIGINTEGER) {
            generateBigInteger(expression, "subtract");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        castIfRequired(expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        castIfRequired(expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getSubtractOpcode());
    }

    public void generate(Multiplication expression) {
        if (expression.getType() == ClassType.BIGINTEGER) {
            generateBigInteger(expression, "multiply");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        castIfRequired(expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        castIfRequired(expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getMultiplyOpcode());
    }

    public void generate(Division expression) {
        if (expression.getType() == ClassType.BIGINTEGER) {
            generateBigInteger(expression, "divide");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        castIfRequired(expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        castIfRequired(expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getDivideOpcode());
    }

    public void generate(Remainder expression) {
        if (expression.getType() == ClassType.BIGINTEGER) {
            generateBigInteger(expression, "remainder");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        castIfRequired(expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        castIfRequired(expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getRemainderOpcode());
    }

    public void generate(Power expression) {
        if (expression.getType() == ClassType.BIGINTEGER) {
            generateBigIntegerPower(expression);
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        castIfRequired(expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        castIfRequired(expression.getRightExpression(), expression.getType());
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
    }

    private void castIfRequired(Expression expression, Type type) {
        if (expression.getType().equals(type)) {
            return;
        }

        if (expression.getType().equals(BultInType.INT)) {
            if (type.equals(BultInType.DOUBLE)) {
                methodVisitor.visitInsn(Opcodes.I2D);
            }
            if (type.equals(BultInType.LONG)) {
                methodVisitor.visitInsn(Opcodes.I2L);
            }
        }

        if (expression.getType() == ClassType.BIGINTEGER) {
            if (type.equals(BultInType.DOUBLE)) {
                methodVisitor
                    .visitMethodInsn(Opcodes.INVOKEVIRTUAL, "Ljava/math/BigInteger;", "doubleValue", "()D", false);
            }
        }
    }

    private void generateBigInteger(ArithmeticExpression expression, String operator) {
        Type type = expression.getType();
        generateSubExpression(expression.getLeftExpression());
        generateSubExpression(expression.getRightExpression());
        methodVisitor
            .visitMethodInsn(Opcodes.INVOKEVIRTUAL, type.getInternalName(), operator,
                "(Ljava/math/BigInteger;)Ljava/math/BigInteger;",
                false);
    }

    private void generateBigIntegerPower(ArithmeticExpression expression) {
        Type type = expression.getType();
        generateSubExpression(expression.getLeftExpression());
        expression.getRightExpression().accept(expressionGenerator);
        methodVisitor
            .visitMethodInsn(Opcodes.INVOKEVIRTUAL, type.getInternalName(), "pow", "(I)Ljava/math/BigInteger;", false);
    }

    private void generateSubExpression(Expression expression) {
        Type type = ClassType.BIGINTEGER;
        if (expression.getType().equals(type)) {
            expression.accept(expressionGenerator);
        } else {
            methodVisitor.visitTypeInsn(Opcodes.NEW, type.getInternalName());
            methodVisitor.visitInsn(Opcodes.DUP);
            expression.accept(expressionGenerator);
            if (expression.getType() != BultInType.STRING) {
                String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/String;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/String", "valueOf", descriptor, false);
            }
            methodVisitor
                .visitMethodInsn(Opcodes.INVOKESPECIAL, type.getInternalName(), "<init>", "(Ljava/lang/String;)V",
                    false);
        }
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
