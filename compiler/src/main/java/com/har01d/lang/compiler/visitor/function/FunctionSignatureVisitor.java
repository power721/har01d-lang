package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.FunctionDeclarationContext;
import com.har01d.lang.antlr.Har01dParser.ParametersListContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;
import java.util.ArrayList;
import org.objectweb.asm.Opcodes;

public class FunctionSignatureVisitor extends Har01dBaseVisitor<FunctionSignature> {

    private final Scope scope;
    private final ExpressionVisitor expressionVisitor;

    public FunctionSignatureVisitor(Scope scope) {
        this.scope = scope;
        this.expressionVisitor = new ExpressionVisitor(scope);
    }

    @Override
    public FunctionSignature visitFunctionDeclaration(FunctionDeclarationContext ctx) {
        String name = ctx.functionName().getText();
        Type type = TypeResolver.resolve(ctx.type());
        ParametersListContext parametersListContext = ctx.parametersList();
        String internalName = name;
        if (scope.getFunctionName() != null) {
            internalName = scope.getFunctionName() + "$" + name;
        }

        int flag = Opcodes.ACC_PUBLIC;
        if (!scope.isClassDeclaration()) {
            flag += Opcodes.ACC_STATIC;
        }

        if (parametersListContext != null) {
            ParameterExpressionListVisitor visitor = new ParameterExpressionListVisitor(expressionVisitor, scope);
            return new FunctionSignature(name, internalName, parametersListContext.accept(visitor), flag, type);
        }
        return new FunctionSignature(name, internalName, new ArrayList<>(), flag, type);
    }

}
