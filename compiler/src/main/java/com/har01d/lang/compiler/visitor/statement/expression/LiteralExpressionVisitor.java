package com.har01d.lang.compiler.visitor.statement.expression;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.LiteralContext;
import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;

public class LiteralExpressionVisitor extends Har01dBaseVisitor<Literal> {

    @Override
    public Literal visitLiteral(LiteralContext ctx) {
        Type type = TypeResolver.resolve(ctx);
        return new Literal(type, fixString(ctx.getText()));
    }

    private String fixString(String value) {
        if (value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'') {
            return value.substring(1, value.length() - 1);
        }

        if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
            return value.substring(1, value.length() - 1);
        }

        return value;
    }

}
