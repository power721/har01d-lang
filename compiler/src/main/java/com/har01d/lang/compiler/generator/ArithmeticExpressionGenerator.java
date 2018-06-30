package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.statement.expression.Addition;
import com.har01d.lang.compiler.domain.statement.expression.Division;
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
import com.har01d.lang.compiler.domain.statement.expression.Remainder;
import com.har01d.lang.compiler.domain.statement.expression.Subtraction;
import com.har01d.lang.compiler.domain.type.Type;
import org.objectweb.asm.MethodVisitor;

public class ArithmeticExpressionGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public ArithmeticExpressionGenerator(ExpressionGenerator expressionGenerator,
        MethodVisitor methodVisitor) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(Addition addition) {
        addition.getLeftExpression().accept(expressionGenerator);
        addition.getRightExpression().accept(expressionGenerator);
        Type type = addition.getType();
        methodVisitor.visitInsn(type.getAddOpcode());
    }

    public void generate(Subtraction subtraction) {
        subtraction.getLeftExpression().accept(expressionGenerator);
        subtraction.getRightExpression().accept(expressionGenerator);
        Type type = subtraction.getType();
        methodVisitor.visitInsn(type.getSubstractOpcode());
    }

    public void generate(Multiplication multiplication) {
        multiplication.getLeftExpression().accept(expressionGenerator);
        multiplication.getRightExpression().accept(expressionGenerator);
        Type type = multiplication.getType();
        methodVisitor.visitInsn(type.getMultiplyOpcode());
    }

    public void generate(Division division) {
        division.getLeftExpression().accept(expressionGenerator);
        division.getRightExpression().accept(expressionGenerator);
        Type type = division.getType();
        methodVisitor.visitInsn(type.getDividOpcode());
    }

    public void generate(Remainder remainder) {
        remainder.getLeftExpression().accept(expressionGenerator);
        remainder.getRightExpression().accept(expressionGenerator);
        Type type = remainder.getType();
        methodVisitor.visitInsn(type.getRemainderOpcode());
    }

}
