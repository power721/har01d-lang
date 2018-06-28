package com.har01d.lang.compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Queue;

import com.har01d.lang.compiler.asm.ByteCodeGenerator;
import com.har01d.lang.compiler.asm.Instruction;

public class Compiler {

    public static void main(String[] args) throws Exception {
        new Compiler().compile(args);
    }

    public void compile(String[] args) throws Exception {
        File file = new File(args[0]);
        String fileName = file.getName();
        String path = file.getAbsolutePath();
        String className = fileName.replace(".hd", "");
        Queue<Instruction> instructions = new SyntaxTreeTraverser().getInstruction(path);

        byte[] byteCode = new ByteCodeGenerator().generateByteCode(instructions, className);
        saveByteCodeToClassFile(fileName, byteCode);
    }

    private void saveByteCodeToClassFile(String fileName, byte[] byteCode) throws IOException {
        String classFile = fileName.replace(".hd", ".class");
        try (OutputStream os = new FileOutputStream(classFile)) {
            os.write(byteCode);
        }
    }

}
