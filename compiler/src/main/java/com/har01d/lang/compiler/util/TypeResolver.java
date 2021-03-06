package com.har01d.lang.compiler.util;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.har01d.lang.antlr.Har01dParser.LiteralContext;
import com.har01d.lang.antlr.Har01dParser.TypeContext;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.ClassType;
import com.har01d.lang.compiler.domain.type.FunctionType;
import com.har01d.lang.compiler.domain.type.Type;

public class TypeResolver {

    private static final Pattern FLOATING_POINT_PATTERN = fpPattern();
    private static final Pattern FUNCTION = Pattern.compile("\\((.*)\\)\\s*->\\s*(.*)");

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
            return Byte.decode(value);
        }
        if (type == BuiltInType.SHORT) {
            return Short.decode(value);
        }
        if (type == BuiltInType.INT) {
            return Integer.decode(value);
        }
        if (type == BuiltInType.LONG) {
            return Long.decode(value);
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
            Integer.decode(value);
            return BuiltInType.INT;
        } catch (NumberFormatException e) {
            // ignore
        }

        try {
            if (value.endsWith("L")) {
                value = value.substring(0, value.length() - 1);
            }
            Long.decode(value);
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

        Matcher m = FUNCTION.matcher(typeName);
        if (m.matches()) {
            List<Type> parameters = new ArrayList<>();
            for (String p : m.group(1).split(",")) {
                parameters.add(resolve(p));
            }
            Type returnType = resolve(m.group(2));
            return new FunctionType(parameters, returnType);
        }

        return new ClassType(typeName);
    }

    public static Type resolve(String typeName) {
        if ("void".equals(typeName)) {
            return BuiltInType.VOID;
        }

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
