package com.har01d.lang.compiler;

import java.io.IOException;
import java.util.Queue;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.har01d.lang.antlr.Har01dLexer;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.asm.Instruction;

public class SyntaxTreeTraverser {
    public Queue<Instruction> getInstruction(String fileName) throws IOException {
        CharStream charStream = CharStreams.fromFileName(fileName);
        Har01dLexer lexer = new Har01dLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Har01dParser parser = new Har01dParser(tokenStream);

        Har01dTreeWalkListener listener = new Har01dTreeWalkListener();
        BaseErrorListener errorListener = new Har01dTreeWalkErrorListener();

        parser.addParseListener(listener);
        parser.addErrorListener(errorListener);
        parser.compilationUnit();

        return listener.getInstructions();
    }
}
