package com.har01d.lang.compiler.visitor.statement.expression;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser.LiteralContext;
import com.har01d.lang.compiler.domain.Literal;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.TypeResolver;

public class LiteralExpressionVisitor extends Har01dBaseVisitor<Literal> {

    @Override
    public Literal visitLiteral(LiteralContext ctx) {
        Type type = TypeResolver.resolve(ctx);
        return new Literal(type, fixValue(type, ctx.getText()));
    }

    private String fixValue(Type type, String value) {
        if (type == BuiltInType.LONG) {
            if (value.endsWith("L")) {
                return value.substring(0, value.length() - 1);
            }
            return value;
        }

        if (type == BuiltInType.STRING) {
            if (value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'') {
                return value.substring(1, value.length() - 1);
            }

            if (value.charAt(0) == '"' && value.charAt(value.length() - 1) == '"') {
                return value.substring(1, value.length() - 1);
            }
        }

        return value;
    }

}
