package com.har01d.lang.compiler.generator;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.statement.expression.Addition;
import com.har01d.lang.compiler.domain.statement.expression.ArithmeticExpression;
import com.har01d.lang.compiler.domain.statement.expression.Division;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
import com.har01d.lang.compiler.domain.statement.expression.Power;
import com.har01d.lang.compiler.domain.statement.expression.Remainder;
import com.har01d.lang.compiler.domain.statement.expression.Subtraction;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeUtil;

public class ArithmeticExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public ArithmeticExpressionGenerator(ExpressionGenerator expressionGenerator,
        MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(Addition expression) {
        if (expression.getType().equals(BuiltInType.STRING)) {
            generateStringAppend(expression);
            return;
        }

        if (expression.getType().equals(ClassType.BIG_INTEGER)) {
            generateBigInteger(expression, "add");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getAddOpcode());
    }

    public void generate(Subtraction expression) {
        if (expression.getType().equals(ClassType.BIG_INTEGER)) {
            generateBigInteger(expression, "subtract");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getSubtractOpcode());
    }

    public void generate(Multiplication expression) {
        if (expression.getType().equals(ClassType.BIG_INTEGER)) {
            generateBigInteger(expression, "multiply");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getMultiplyOpcode());
    }

    public void generate(Division expression) {
        if (expression.getType().equals(ClassType.BIG_INTEGER)) {
            generateBigInteger(expression, "divide");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getDivideOpcode());
    }

    public void generate(Remainder expression) {
        if (expression.getType().equals(ClassType.BIG_INTEGER)) {
            generateBigInteger(expression, "remainder");
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getRightExpression(), expression.getType());
        Type type = expression.getType();
        methodVisitor.visitInsn(type.getRemainderOpcode());
    }

    public void generate(Power expression) {
        if (expression.getType().equals(ClassType.BIG_INTEGER)) {
            generateBigIntegerPower(expression);
            return;
        }

        expression.getLeftExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getLeftExpression(), expression.getType());
        expression.getRightExpression().accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression.getRightExpression(), expression.getType());
        methodVisitor.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Math", "pow", "(DD)D", false);
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
        Type type = ClassType.BIG_INTEGER;
        if (expression.getType().equals(type)) {
            expression.accept(expressionGenerator);
        } else {
            TypeUtil.cast2BigInteger(methodVisitor, expressionGenerator, expression);
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
