package com.har01d.lang.compiler.visitor;

import java.util.List;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.ClassDeclaration;
import com.har01d.lang.compiler.domain.MetaData;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.util.FunctionUtil;

public class ClassVisitor extends Har01dBaseVisitor<ClassDeclaration> {

    @Override
    public ClassDeclaration visitClassDeclaration(Har01dParser.ClassDeclarationContext ctx) {
        String className = ctx.className().getText();
        Scope scope = new Scope(new MetaData(className, true));
        List<Function> functions = FunctionUtil.getFunctions(ctx.classBody().function(), scope);

        return new ClassDeclaration(className, functions);
    }

}
