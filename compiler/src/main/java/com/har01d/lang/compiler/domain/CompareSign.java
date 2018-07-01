package com.har01d.lang.compiler.domain;

import java.util.Arrays;
import org.objectweb.asm.Opcodes;

public enum CompareSign {
    EQUAL("==", Opcodes.IFEQ),
    NOT_EQUAL("!=", Opcodes.IFNE),
    LESS("<", Opcodes.IFLT),
    GREATER(">", Opcodes.IFGT),
    LESS_OR_EQUAL("<=", Opcodes.IFLE),
    GRATER_OR_EQUAL(">=", Opcodes.IFGE);

    private final String sign;
    private final int opcode;

    CompareSign(String s, int opcode) {
        sign = s;
        this.opcode = opcode;
    }

    public static CompareSign fromString(String sign) {
        return Arrays.stream(values()).filter(cmpSign -> cmpSign.sign.equals(sign))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Sign not implemented"));
    }

    public int getOpcode() {
        return opcode;
    }
}
