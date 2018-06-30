package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.LocalVariableReference;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionParameter;
import org.objectweb.asm.MethodVisitor;

public class ExpressionGenerator {

    private final LiteralExpressionGenerator literalExpressionGenerator;
    private final ParameterExpressionGenerator parameterExpressionGenerator;
    private final ReferenceExpressionGenerator referenceExpressionGenerator;

    public ExpressionGenerator(MethodVisitor methodVisitor, Scope scope) {
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

}
