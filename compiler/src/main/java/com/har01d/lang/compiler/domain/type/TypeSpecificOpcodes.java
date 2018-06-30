package com.har01d.lang.compiler.domain.type;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.ARETURN;
import static org.objectweb.asm.Opcodes.ASTORE;
import static org.objectweb.asm.Opcodes.DADD;
import static org.objectweb.asm.Opcodes.DDIV;
import static org.objectweb.asm.Opcodes.DLOAD;
import static org.objectweb.asm.Opcodes.DMUL;
import static org.objectweb.asm.Opcodes.DREM;
import static org.objectweb.asm.Opcodes.DRETURN;
import static org.objectweb.asm.Opcodes.DSTORE;
import static org.objectweb.asm.Opcodes.DSUB;
import static org.objectweb.asm.Opcodes.FADD;
import static org.objectweb.asm.Opcodes.FDIV;
import static org.objectweb.asm.Opcodes.FLOAD;
import static org.objectweb.asm.Opcodes.FMUL;
import static org.objectweb.asm.Opcodes.FREM;
import static org.objectweb.asm.Opcodes.FRETURN;
import static org.objectweb.asm.Opcodes.FSTORE;
import static org.objectweb.asm.Opcodes.FSUB;
import static org.objectweb.asm.Opcodes.IADD;
import static org.objectweb.asm.Opcodes.IDIV;
import static org.objectweb.asm.Opcodes.ILOAD;
import static org.objectweb.asm.Opcodes.IMUL;
import static org.objectweb.asm.Opcodes.IREM;
import static org.objectweb.asm.Opcodes.IRETURN;
import static org.objectweb.asm.Opcodes.ISTORE;
import static org.objectweb.asm.Opcodes.ISUB;
import static org.objectweb.asm.Opcodes.LADD;
import static org.objectweb.asm.Opcodes.LDIV;
import static org.objectweb.asm.Opcodes.LLOAD;
import static org.objectweb.asm.Opcodes.LMUL;
import static org.objectweb.asm.Opcodes.LREM;
import static org.objectweb.asm.Opcodes.LRETURN;
import static org.objectweb.asm.Opcodes.LSTORE;
import static org.objectweb.asm.Opcodes.LSUB;
import static org.objectweb.asm.Opcodes.RETURN;

public enum TypeSpecificOpcodes {

    INT(ILOAD, ISTORE, IRETURN, IADD, ISUB, IMUL, IDIV, IREM), //values (-127,127) - one byte.
    LONG(LLOAD, LSTORE, LRETURN, LADD, LSUB, LMUL, LDIV, LREM),
    FLOAT(FLOAD, FSTORE, FRETURN, FADD, FSUB, FMUL, FDIV, FREM),
    DOUBLE(DLOAD, DSTORE, DRETURN, DADD, DSUB, DMUL, DDIV, DREM),
    VOID(ALOAD, ASTORE, RETURN, 0, 0, 0, 0, 0),
    OBJECT(ALOAD, ASTORE, ARETURN, 0, 0, 0, 0, 0);

    private final int load;
    private final int store;
    private final int ret;
    private final int add;
    private final int sub;
    private final int mul;
    private final int div;
    private final int rem;

    TypeSpecificOpcodes(int load, int store, int ret, int add, int sub, int mul, int div, int rem) {
        this.load = load;
        this.store = store;
        this.ret = ret;
        this.add = add;
        this.sub = sub;
        this.mul = mul;
        this.div = div;
        this.rem = rem;
    }

    public int getLoad() {
        return load;
    }

    public int getStore() {
        return store;
    }

    public int getReturn() {
        return ret;
    }

    public int getAdd() {
        return add;
    }

    public int getSubtract() {
        return sub;
    }

    public int getMultiply() {
        return mul;
    }

    public int getDivide() {
        return div;
    }

    public int getRemainder() {
        return rem;
    }

}

