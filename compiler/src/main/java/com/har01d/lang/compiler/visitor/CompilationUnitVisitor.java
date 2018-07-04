package com.har01d.lang.compiler.visitor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.antlr.Har01dParser.CompilationUnitContext;
import com.har01d.lang.compiler.domain.ClassDeclaration;
import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.domain.MetaData;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;
import com.har01d.lang.compiler.visitor.function.FunctionVisitor;
import com.har01d.lang.compiler.visitor.statement.StatementVisitor;

public class CompilationUnitVisitor extends Har01dBaseVisitor<CompilationUnit> {

    private final MetaData metaData;

    public CompilationUnitVisitor(MetaData metaData) {
        this.metaData = metaData;
    }

    @Override
    public CompilationUnit visitCompilationUnit(CompilationUnitContext ctx) {
        Scope scope = new Scope(metaData);
        FunctionVisitor functionVisitor = new FunctionVisitor(scope);
        List<Function> functions = ctx.function().stream().map(e -> e.accept(functionVisitor))
                                        .peek(e -> scope.addSignature(e.getFunctionSignature()))
                                        .collect(Collectors.toList());

        StatementVisitor statementVisitor = new StatementVisitor(scope);
        List<Statement> statements = ctx.statement().stream().map(e -> e.accept(statementVisitor))
                                        .collect(Collectors.toList());

        ClassVisitor classVisitor = new ClassVisitor();
        List<ClassDeclaration> classDeclarations = new ArrayList<>();
        for (Har01dParser.ClassDeclarationContext cdc : ctx.classDeclaration()) {
            ClassDeclaration declaration = cdc.accept(classVisitor);
            if (scope.isClassExist(declaration.getName())) {
                throw new InvalidSyntaxException("Class " + declaration.getName() + " already declared!", cdc);
            }
            scope.addClass(declaration.getName());
            classDeclarations.add(declaration);
        }

        String name = getName(classDeclarations, scope);
        return new CompilationUnit(statements, functions, classDeclarations, scope, name);
    }

    private String getName(List<ClassDeclaration> classDeclarations, Scope scope) {
        if (isClassNameExist(classDeclarations, scope.getClassName())) {
            String name = scope.getClassName() + "HD";
            int i = 0;
            while (true) {
                if (isClassNameExist(classDeclarations, name)) {
                    name = scope.getClassName() + "HD" + i++;
                } else {
                    break;
                }
            }
            return name;
        }
        return scope.getClassName();
    }

    private boolean isClassNameExist(List<ClassDeclaration> classDeclarations, String name) {
        return classDeclarations.stream().anyMatch(e -> e.getName().equals(name));
    }

}
