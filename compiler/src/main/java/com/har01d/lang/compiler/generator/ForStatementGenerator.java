package com.har01d.lang.compiler.generator;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.CompareSign;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.statement.RangedForStatement;
import com.har01d.lang.compiler.domain.statement.expression.RelationalExpression;
import com.har01d.lang.compiler.domain.variable.LocalVariableReference;

public class ForStatementGenerator {

    private final ClassWriter classWriter;
    private final MethodVisitor methodVisitor;

    public ForStatementGenerator(ClassWriter classWriter, MethodVisitor methodVisitor) {
        this.classWriter = classWriter;
        this.methodVisitor = methodVisitor;
    }

    public void generate(RangedForStatement forStatement) {
        Scope scope = forStatement.getVariable().getScope();
        StatementGenerator statementGenerator = new StatementGenerator(classWriter, methodVisitor, scope);
        forStatement.getIterator().accept(statementGenerator);
        Label incrementationSection = new Label();
        Label endLoopSection = new Label();
        int step = forStatement.getStep();

        LocalVariableReference reference = new LocalVariableReference(forStatement.getVariable());
        RelationalExpression conditionalExpression;
        if (step > 0) {
            conditionalExpression = new RelationalExpression(reference, forStatement.getEndExpression(),
                                            CompareSign.LESS_OR_EQUAL);
        } else {
            conditionalExpression = new RelationalExpression(reference, forStatement.getEndExpression(),
                                            CompareSign.GRATER_OR_EQUAL);
        }

        conditionalExpression.accept(statementGenerator);
        methodVisitor.visitJumpInsn(Opcodes.IFNE, incrementationSection);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLoopSection);

        methodVisitor.visitLabel(incrementationSection);
        forStatement.getStatement().accept(statementGenerator);
        methodVisitor.visitIincInsn(forStatement.getVariable().getIndex(), step);
        conditionalExpression.accept(statementGenerator);
        methodVisitor.visitJumpInsn(Opcodes.IFNE, incrementationSection);
        methodVisitor.visitJumpInsn(Opcodes.GOTO, endLoopSection);

        methodVisitor.visitLabel(endLoopSection);
    }

}
