package com.har01d.lang.compiler.generator;

import java.util.List;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import com.har01d.lang.compiler.domain.statement.PrintStatement;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;

public class PrintStatementGenerator {

    private final ExpressionGenerator expressionGenerator;
    private final MethodVisitor methodVisitor;

    public PrintStatementGenerator(MethodVisitor methodVisitor, ExpressionGenerator expressionGenerator) {
        this.expressionGenerator = expressionGenerator;
        this.methodVisitor = methodVisitor;
    }

    public void generate(PrintStatement printStatement) {
        List<Expression> expressions = printStatement.getExpressions();
        methodVisitor.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");

        String descriptor;
        if (expressions.size() == 1) {
            Expression expression = expressions.get(0);
            expression.accept(expressionGenerator);
            Type type = expression.getType();
            if (type instanceof ClassType) {
                descriptor = "(Ljava/lang/Object;)V";
            } else {
                descriptor = "(" + type.getDescriptor() + ")V";
            }
        } else {
            generateStringAppend(expressions);
            descriptor = "(Ljava/lang/String;)V";
        }

        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", descriptor, false);
    }

    private void generateStringAppend(List<Expression> expressions) {
        methodVisitor.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
        methodVisitor.visitInsn(Opcodes.DUP);
        methodVisitor.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V", false);

        boolean first = true;
        for (Expression expression : expressions) {
            if (!first) {
                methodVisitor.visitLdcInsn(" ");
                String descriptor = "(Ljava/lang/String;)Ljava/lang/StringBuilder;";
                methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor,
                                                false);
            }
            expression.accept(expressionGenerator);
            String descriptor = "(" + expression.getType().getDescriptor() + ")Ljava/lang/StringBuilder;";
            methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", descriptor,
                                            false);
            first = false;
        }

        methodVisitor.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString",
                                        "()Ljava/lang/String;", false);
    }

}
