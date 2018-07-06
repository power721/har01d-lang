package com.har01d.lang.compiler.generator;

import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
import com.har01d.lang.compiler.util.TypeUtil;

public class AssignmentStatementGenerator {

    private final MethodVisitor methodVisitor;
    private final ExpressionGenerator expressionGenerator;

    public AssignmentStatementGenerator(MethodVisitor methodVisitor, ExpressionGenerator expressionGenerator) {
        this.methodVisitor = methodVisitor;
        this.expressionGenerator = expressionGenerator;
    }

    public void generator(Assignment assignment) {
        LocalVariable localVariable = assignment.getVariable();
        int index = localVariable.getIndex();
        Type variableType = localVariable.getType();

        Expression expression = assignment.getExpression();
        Type expressionType = expression.getType();
        if (variableType.equals(ClassType.BIG_INTEGER) && !expressionType.equals(ClassType.BIG_INTEGER)) {
            TypeUtil.cast2BigInteger(methodVisitor, expressionGenerator, expression);
        } else {
            expression.accept(expressionGenerator);
            TypeUtil.castIfRequired(methodVisitor, expression, variableType);
        }

        methodVisitor.visitVarInsn(variableType.getStoreVariableOpcode(), index);
    }

}
