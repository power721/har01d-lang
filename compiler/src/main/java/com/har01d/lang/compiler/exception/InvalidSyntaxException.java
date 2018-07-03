package com.har01d.lang.compiler.exception;

import org.antlr.v4.runtime.ParserRuleContext;

public class InvalidSyntaxException extends RuntimeException {

    private final int line;

    public InvalidSyntaxException(ParserRuleContext ctx) {
        this.line = ctx.start.getLine();
    }

    public InvalidSyntaxException(String message, ParserRuleContext ctx) {
        super(message);
        this.line = ctx.start.getLine();
    }

    public InvalidSyntaxException(String message, Throwable cause, ParserRuleContext ctx) {
        super(message, cause);
        this.line = ctx.start.getLine();
    }

    public InvalidSyntaxException(Throwable cause, ParserRuleContext ctx) {
        super(cause);
        this.line = ctx.start.getLine();
    }

    public int getLine() {
        return line;
    }

}
