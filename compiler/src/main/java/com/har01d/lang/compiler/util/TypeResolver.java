package com.har01d.lang.compiler.util;

import com.har01d.lang.antlr.Har01dParser.LiteralContext;
import com.har01d.lang.compiler.domain.type.BultInType;
import com.har01d.lang.compiler.domain.type.Type;

public class TypeResolver {

    public static Object getValue(Type type, String value) {
        if (type == BultInType.BYTE) {
            return Byte.valueOf(value);
        }
        if (type == BultInType.SHORT) {
            return Short.valueOf(value);
        }
        if (type == BultInType.INT) {
            return Integer.valueOf(value);
        }
        if (type == BultInType.LONG) {
            return Long.valueOf(value);
        }
        if (type == BultInType.BOOLEAN) {
            return Boolean.valueOf(value);
        }
        if (type == BultInType.FLOAT) {
            return Float.valueOf(value);
        }
        if (type == BultInType.DOUBLE) {
            return Double.valueOf(value);
        }
        if (type == BultInType.STRING) {
            return value;
        }
        throw new AssertionError("unsupported type " + type);
    }

    public static Type resolve(LiteralContext ctx) {
        String value = ctx.getText();
        if (value == null || value.isEmpty()) {
            return BultInType.VOID;
        }
        if (ctx.NUMBER() != null) {
            // TODO: double, long
            return BultInType.INT;
        } else if (ctx.BOOL() != null) {
            return BultInType.BOOLEAN;
        }
        return BultInType.STRING;
    }

}
