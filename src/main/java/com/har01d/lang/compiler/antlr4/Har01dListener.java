package com.har01d.lang.compiler.antlr4;// Generated from Har01d.g4 by ANTLR 4.7.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link Har01dParser}.
 */
public interface Har01dListener extends ParseTreeListener {
    /**
     * Enter a parse tree produced by {@link Har01dParser#compilationUnit}.
     * @param ctx the parse tree
     */
    void enterCompilationUnit(Har01dParser.CompilationUnitContext ctx);

    /**
     * Exit a parse tree produced by {@link Har01dParser#compilationUnit}.
     * @param ctx the parse tree
     */
    void exitCompilationUnit(Har01dParser.CompilationUnitContext ctx);

    /**
     * Enter a parse tree produced by {@link Har01dParser#variable}.
     * @param ctx the parse tree
     */
    void enterVariable(Har01dParser.VariableContext ctx);

    /**
     * Exit a parse tree produced by {@link Har01dParser#variable}.
     * @param ctx the parse tree
     */
    void exitVariable(Har01dParser.VariableContext ctx);

    /**
     * Enter a parse tree produced by {@link Har01dParser#assign}.
     * @param ctx the parse tree
     */
    void enterAssign(Har01dParser.AssignContext ctx);

    /**
     * Exit a parse tree produced by {@link Har01dParser#assign}.
     * @param ctx the parse tree
     */
    void exitAssign(Har01dParser.AssignContext ctx);

    /**
     * Enter a parse tree produced by {@link Har01dParser#print}.
     * @param ctx the parse tree
     */
    void enterPrint(Har01dParser.PrintContext ctx);

    /**
     * Exit a parse tree produced by {@link Har01dParser#print}.
     * @param ctx the parse tree
     */
    void exitPrint(Har01dParser.PrintContext ctx);

    /**
     * Enter a parse tree produced by {@link Har01dParser#expression}.
     * @param ctx the parse tree
     */
    void enterExpression(Har01dParser.ExpressionContext ctx);

    /**
     * Exit a parse tree produced by {@link Har01dParser#expression}.
     * @param ctx the parse tree
     */
    void exitExpression(Har01dParser.ExpressionContext ctx);

    /**
     * Enter a parse tree produced by {@link Har01dParser#value}.
     * @param ctx the parse tree
     */
    void enterValue(Har01dParser.ValueContext ctx);

    /**
     * Exit a parse tree produced by {@link Har01dParser#value}.
     * @param ctx the parse tree
     */
    void exitValue(Har01dParser.ValueContext ctx);
}