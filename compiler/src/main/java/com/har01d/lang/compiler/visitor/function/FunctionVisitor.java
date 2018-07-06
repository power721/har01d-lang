package com.har01d.lang.compiler.visitor.function;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.FunctionContext;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Constructor;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.expression.Expression;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.visitor.statement.BlockStatementVisitor;
import com.har01d.lang.compiler.visitor.statement.expression.ExpressionVisitor;
import java.util.ArrayList;
import java.util.Collections;
import org.objectweb.asm.Opcodes;

public class FunctionVisitor extends Har01dBaseVisitor<Function> {

    private final Scope scope;
    private FunctionSignature functionSignature;

    public FunctionVisitor(Scope scope, FunctionSignature functionSignature) {
        this.scope = scope;
        this.functionSignature = functionSignature;
    }

    @Override
    public Function visitFunction(FunctionContext ctx) {
        Scope scope = new Scope(this.scope, true);
        if (functionSignature == null) {
            functionSignature = ctx.functionDeclaration().accept(new FunctionSignatureVisitor(scope));
        }

        if (scope.isClassDeclaration()) {
            scope.addLocalValue("this", scope.getClassType(), true, ctx);
        }

        scope.setFunctionSignature(functionSignature);
        functionSignature.getParameters().forEach(e -> scope.addLocalValue(e.getName(), e.getType(), true, ctx));
        Block block = null;
        if (ctx.block() != null) {
            BlockStatementVisitor blockStatementVisitor = new BlockStatementVisitor(scope);
            block = ctx.block().accept(blockStatementVisitor);
        } else if (ctx.expression() != null) {
            ExpressionVisitor expressionVisitor = new ExpressionVisitor(scope);
            Expression expression = ctx.expression().accept(expressionVisitor);
            if (ctx.functionDeclaration().type() == null) {
                functionSignature.setReturnType(expression.getType());
            }
            block = new Block(scope, Collections.emptyList(), Collections.singletonList(expression));
        }

        if (block != null) {
            block.getScope().getImplicitVariables().forEach(e -> functionSignature.addImplicitParameter(e));
        }

        // TODO: constructor
        return new Function(functionSignature, block);
    }

    @Override
    public Constructor visitConstructor(Har01dParser.ConstructorContext ctx) {
        Scope scope = new Scope(this.scope, true);
        String name = scope.getClassName();
        Type type = BuiltInType.VOID;
        Har01dParser.ParametersListContext parametersListContext = ctx.parametersList();

        FunctionSignature signature;
        int flag = Opcodes.ACC_PUBLIC;
        if (parametersListContext != null) {
            ExpressionVisitor expressionVisitor = new ExpressionVisitor(scope);
            ParameterExpressionListVisitor visitor = new ParameterExpressionListVisitor(expressionVisitor, scope);
            signature = new FunctionSignature(name, "<init>", parametersListContext.accept(visitor), flag, type);
        } else {
            signature = new FunctionSignature(name, "<init>", new ArrayList<>(), flag, type);
        }

        scope.addLocalValue("this", scope.getClassType(), true, ctx);
        signature.getParameters().forEach(e -> scope.addLocalValue(e.getName(), e.getType(), true, ctx));

        BlockStatementVisitor blockStatementVisitor = new BlockStatementVisitor(scope);
        Block block = ctx.block().accept(blockStatementVisitor);
        return new Constructor(signature, block);
    }

}
