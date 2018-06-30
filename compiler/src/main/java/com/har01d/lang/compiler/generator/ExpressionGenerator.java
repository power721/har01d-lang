package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionCall;
import com.har01d.lang.compiler.domain.function.FunctionParameter;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;
import org.objectweb.asm.MethodVisitor;

public class ExpressionGenerator {

    private final CallExpressionGenerator callExpressionGenerator;
    private final LiteralExpressionGenerator literalExpressionGenerator;
    private final ParameterExpressionGenerator parameterExpressionGenerator;
    private final ReferenceExpressionGenerator referenceExpressionGenerator;

    public ExpressionGenerator(MethodVisitor methodVisitor, Scope scope) {
        callExpressionGenerator = new CallExpressionGenerator(this, methodVisitor, scope);
        literalExpressionGenerator = new LiteralExpressionGenerator(methodVisitor);
        parameterExpressionGenerator = new ParameterExpressionGenerator(methodVisitor, scope);
        referenceExpressionGenerator = new ReferenceExpressionGenerator(methodVisitor, scope);
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

}
