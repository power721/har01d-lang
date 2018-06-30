package com.har01d.lang.compiler.generator;

import com.har01d.lang.compiler.domain.LocalVariable;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.Assignment;
import com.har01d.lang.compiler.domain.statement.Expression;
import com.har01d.lang.compiler.domain.type.Type;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

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
        Expression expression = assignment.getExpression();
        Type expressionType = expression.getType();
        if (scope.isLocalVariableExists(varName)) {
            int index = scope.getLocalVariableIndex(varName);
            LocalVariable localVariable = scope.getLocalVariable(varName);
            Type variableType = localVariable.getType();
            expression.accept(expressionGenerator);
            castIfNecessary(expressionType, variableType);
            methodVisitor.visitVarInsn(expressionType.getStoreVariableOpcode(), index);
        }
    }

    private void castIfNecessary(Type expressionType, Type variableType) {
        if (!expressionType.equals(variableType)) {
            methodVisitor.visitTypeInsn(Opcodes.CHECKCAST, variableType.getInternalName());
        }
    }

}
