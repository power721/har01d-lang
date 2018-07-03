package com.har01d.lang.compiler.generator;

import org.objectweb.asm.MethodVisitor;

import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.domain.variable.LocalVariable;
import com.har01d.lang.compiler.util.TypeUtil;

public class AssignmentStatementGenerator {

    private final MethodVisitor methodVisitor;
    private final ExpressionGenerator expressionGenerator;
    private final Scope scope;

    public AssignmentStatementGenerator(MethodVisitor methodVisitor,
        ExpressionGenerator expressionGenerator, Scope scope) {
        this.methodVisitor = methodVisitor;
        this.expressionGenerator = expressionGenerator;
        this.scope = scope;
    }

    public void generator(Assignment assignment) {
        String varName = assignment.getVarName();
        if (scope.isLocalVariableExists(varName)) {
            int index = scope.getLocalVariableIndex(varName);
            LocalVariable localVariable = scope.getLocalVariable(varName);
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

}
