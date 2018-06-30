package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.statement.expression.Addition;
import com.har01d.lang.compiler.domain.statement.expression.Division;
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
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

    public void generate(Multiplication subtraction) {
        subtraction.getLeftExpression().accept(expressionGenerator);
        subtraction.getRightExpression().accept(expressionGenerator);
        Type type = subtraction.getType();
        methodVisitor.visitInsn(type.getMultiplyOpcode());
    }

    public void generate(Division d) {
        d.getLeftExpression().accept(expressionGenerator);
        d.getRightExpression().accept(expressionGenerator);
        Type type = d.getType();
        methodVisitor.visitInsn(type.getDividOpcode());
    }

}
