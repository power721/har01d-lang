package com.har01d.lang.compiler.parser;

import com.har01d.lang.antlr.Har01dLexer;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.listener.Har01dTreeWalkErrorListener;
import com.har01d.lang.compiler.visitor.CompilationUnitVisitor;
import java.io.IOException;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

public class Parser {

    public CompilationUnit getCompilationUnit(String fileName) throws IOException {
        CharStream charStream = CharStreams.fromFileName(fileName);
        Har01dLexer lexer = new Har01dLexer(charStream);
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        Har01dParser parser = new Har01dParser(tokenStream);

        BaseErrorListener errorListener = new Har01dTreeWalkErrorListener();

        parser.addErrorListener(errorListener);
        CompilationUnitVisitor compilationUnitVisitor = new CompilationUnitVisitor();

        return parser.compilationUnit().accept(compilationUnitVisitor);
    }

}
