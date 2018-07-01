package com.har01d.lang.compiler.util;

import com.har01d.lang.antlr.Har01dParser.LiteralContext;
import com.har01d.lang.antlr.Har01dParser.TypeContext;
import com.har01d.lang.compiler.domain.type.BultInType;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.Type;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

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
            return resolveNumber(value);
        } else if (ctx.BOOL() != null) {
            return BultInType.BOOLEAN;
        }
        return BultInType.STRING;
    }

    private static Type resolveNumber(String value) {
        try {
            Integer.parseInt(value);
            return BultInType.INT;
        } catch (NumberFormatException e) {
            // ignore
        }

        try {
            Long.parseLong(value);
            return BultInType.LONG;
        } catch (NumberFormatException e) {
            // ignore
        }

        try {
            new BigInteger(value);
            return ClassType.BIGINTEGER;
        } catch (NumberFormatException e) {
            // ignore
        }

        if (FLOATING_POINT_PATTERN.matcher(value).matches()) {
            try {
                Double.parseDouble(value);
                return BultInType.DOUBLE;
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        return BultInType.STRING;
    }

    public static Type resolve(TypeContext ctx) {
        if (ctx == null) {
            return BultInType.VOID;
        }

        String typeName = ctx.getText();
        if ("java.lang.String".equals(typeName)) {
            return BultInType.STRING;
        }

        Optional<BultInType> type = Arrays.stream(BultInType.values()).filter(e -> e.getName().equals(typeName))
            .findFirst();
        if (type.isPresent()) {
            return type.get();
        }

        return new ClassType(typeName);
    }

}
