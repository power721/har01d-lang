package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.LocalVariableReference;
import com.har01d.lang.compiler.domain.Scope;
import org.objectweb.asm.MethodVisitor;

public class ExpressionGenerator {

    private final LiteralExpressionGenerator literalExpressionGenerator;
    private final ReferenceExpressionGenerator referenceExpressionGenerator;

    public ExpressionGenerator(MethodVisitor methodVisitor, Scope scope) {
        literalExpressionGenerator = new LiteralExpressionGenerator(methodVisitor);
        referenceExpressionGenerator = new ReferenceExpressionGenerator(methodVisitor, scope);
    }

    public void generate(Literal literal) {
        literalExpressionGenerator.generate(literal);
    }

    public void generate(LocalVariableReference localVariableReference) {
        referenceExpressionGenerator.generate(localVariableReference);
    }

}
