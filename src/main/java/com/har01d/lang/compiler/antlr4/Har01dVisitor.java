package com.har01d.lang.compiler.antlr4;// Generated from Har01d.g4 by ANTLR 4.7.1

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link Har01dParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface Har01dVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link Har01dParser#compilationUnit}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitCompilationUnit(Har01dParser.CompilationUnitContext ctx);

    /**
     * Visit a parse tree produced by {@link Har01dParser#variable}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitVariable(Har01dParser.VariableContext ctx);

    /**
     * Visit a parse tree produced by {@link Har01dParser#assign}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssign(Har01dParser.AssignContext ctx);

    /**
     * Visit a parse tree produced by {@link Har01dParser#print}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitPrint(Har01dParser.PrintContext ctx);

    /**
     * Visit a parse tree produced by {@link Har01dParser#expression}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExpression(Har01dParser.ExpressionContext ctx);

    /**
     * Visit a parse tree produced by {@link Har01dParser#value}.
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitValue(Har01dParser.ValueContext ctx);
}
