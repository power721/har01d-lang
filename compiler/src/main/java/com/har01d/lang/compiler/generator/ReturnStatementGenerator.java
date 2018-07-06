package com.har01d.lang.compiler.generator;

import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.domain.statement.ReturnStatement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeUtil;

public class ReturnStatementGenerator {

    private final MethodVisitor methodVisitor;
    private final ExpressionGenerator expressionGenerator;

    public ReturnStatementGenerator(MethodVisitor methodVisitor,
        ExpressionGenerator expressionGenerator) {
        this.methodVisitor = methodVisitor;
        this.expressionGenerator = expressionGenerator;
    }

    public void generate(ReturnStatement returnStatement) {
        Expression expression = returnStatement.getExpression();
        Type type = returnStatement.getType();
        expression.accept(expressionGenerator);
        TypeUtil.castIfRequired(methodVisitor, expression, type);
        methodVisitor.visitInsn(type.getReturnOpcode());
    }

}
