package com.har01d.lang.compiler.util;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import com.har01d.lang.antlr.Har01dParser.LiteralContext;
import com.har01d.lang.antlr.Har01dParser.TypeContext;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;

public class TypeResolver {

    private static final Pattern FLOATING_POINT_PATTERN = fpPattern();

    private static Pattern fpPattern() {
        String decimal = "(?:\\d++(?:\\.\\d*+)?|\\.\\d++)";
        String completeDec = decimal + "(?:[eE][+-]?\\d++)?[fFdD]?";
        String hex = "(?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)";
        String completeHex = "0[xX]" + hex + "[pP][+-]?\\d++[fFdD]?";
        String fpPattern = "[+-]?(?:NaN|Infinity|" + completeDec + "|" + completeHex + ")";
        return Pattern.compile(fpPattern);
    }

    public static Object getValue(Type type, String value) {
        if (type == BuiltInType.BYTE) {
            return Byte.valueOf(value);
        }
        if (type == BuiltInType.SHORT) {
            return Short.valueOf(value);
        }
        if (type == BuiltInType.INT) {
            return Integer.valueOf(value);
        }
        if (type == BuiltInType.LONG) {
            return Long.valueOf(value);
        }
        if (type == BuiltInType.BOOLEAN) {
            return Boolean.valueOf(value);
        }
        if (type == BuiltInType.FLOAT) {
            return Float.valueOf(value);
        }
        if (type == BuiltInType.DOUBLE) {
            return Double.valueOf(value);
        }
        if (type == BuiltInType.STRING) {
            return value;
        }
        throw new AssertionError("unsupported type " + type);
    }

    public static Type resolve(LiteralContext ctx) {
        String value = ctx.getText();
        if (value == null || value.isEmpty()) {
            return BuiltInType.VOID;
        }
        if (ctx.NUMBER() != null) {
            return resolveNumber(value);
        } else if (ctx.BOOL() != null) {
            return BuiltInType.BOOLEAN;
        }
        return BuiltInType.STRING;
    }

    private static Type resolveNumber(String value) {
        try {
            Integer.parseInt(value);
            return BuiltInType.INT;
        } catch (NumberFormatException e) {
            // ignore
        }

        try {
            Long.parseLong(value);
            return BuiltInType.LONG;
        } catch (NumberFormatException e) {
            // ignore
        }

        try {
            new BigInteger(value);
            return ClassType.BIG_INTEGER;
        } catch (NumberFormatException e) {
            // ignore
        }

        if (FLOATING_POINT_PATTERN.matcher(value).matches()) {
            try {
                Double.parseDouble(value);
                return BuiltInType.DOUBLE;
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        return BuiltInType.STRING;
    }

    public static Type resolve(TypeContext ctx) {
        if (ctx == null) {
            return BuiltInType.VOID;
        }

        String typeName = ctx.getText();
        if ("java.lang.String".equals(typeName)) {
            return BuiltInType.STRING;
        }

        Optional<BuiltInType> type = Arrays.stream(BuiltInType.values()).filter(e -> e.getName().equals(typeName))
            .findFirst();
        if (type.isPresent()) {
            return type.get();
        }

        return new ClassType(typeName);
    }

}
