package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionCall;
import com.har01d.lang.compiler.domain.function.FunctionParameter;
import com.har01d.lang.compiler.domain.statement.expression.Addition;
import com.har01d.lang.compiler.domain.statement.expression.Division;
import com.har01d.lang.compiler.domain.statement.expression.Multiplication;
import com.har01d.lang.compiler.domain.statement.expression.Power;
import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;
import com.har01d.lang.compiler.domain.statement.expression.Remainder;
import com.har01d.lang.compiler.domain.statement.expression.Subtraction;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;
import org.objectweb.asm.MethodVisitor;

public class ExpressionGenerator {

    private final CallExpressionGenerator callExpressionGenerator;
    private final LiteralExpressionGenerator literalExpressionGenerator;
    private final ParameterExpressionGenerator parameterExpressionGenerator;
    private final ReferenceExpressionGenerator referenceExpressionGenerator;
    private final ArithmeticExpressionGenerator arithmeticExpressionGenerator;
    private final ConditionalExpressionGenerator conditionalExpressionGenerator;

    public ExpressionGenerator(MethodVisitor methodVisitor, Scope scope) {
        literalExpressionGenerator = new LiteralExpressionGenerator(methodVisitor);
        parameterExpressionGenerator = new ParameterExpressionGenerator(methodVisitor, scope);
        referenceExpressionGenerator = new ReferenceExpressionGenerator(methodVisitor, scope);
        callExpressionGenerator = new CallExpressionGenerator(this, methodVisitor, scope);
        arithmeticExpressionGenerator = new ArithmeticExpressionGenerator(this, methodVisitor);
        conditionalExpressionGenerator = new ConditionalExpressionGenerator(this, methodVisitor);
    }

    public void generate(Literal literal) {
        literalExpressionGenerator.generate(literal);
    }

    public void generate(LocalVariableReference localVariableReference) {
        referenceExpressionGenerator.generate(localVariableReference);
    }

    public void generate(FunctionParameter functionParameter) {
        parameterExpressionGenerator.generate(functionParameter);
    }

    public void generate(FunctionCall functionCall) {
        callExpressionGenerator.generate(functionCall);
    }

    public void generate(Addition expression) {
        arithmeticExpressionGenerator.generate(expression);
    }

    public void generate(Subtraction expression) {
        arithmeticExpressionGenerator.generate(expression);
    }

    public void generate(Multiplication expression) {
        arithmeticExpressionGenerator.generate(expression);
    }

    public void generate(Division expression) {
        arithmeticExpressionGenerator.generate(expression);
    }

    public void generate(Remainder expression) {
        arithmeticExpressionGenerator.generate(expression);
    }

    public void generate(Power expression) {
        arithmeticExpressionGenerator.generate(expression);
    }

    public void generate(RelationalExpression relationalExpression) {
        conditionalExpressionGenerator.generate(relationalExpression);
    }

}
