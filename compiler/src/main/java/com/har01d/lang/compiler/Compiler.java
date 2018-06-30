package com.har01d.lang.compiler;

import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.generator.ByteCodeGenerator;
import com.har01d.lang.compiler.parser.Parser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Compiler {

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java com.har01d.lang.compiler.Compiler <FILE_NAME>.hd");
            System.exit(1);
        }

        new Compiler().compile(args);
    }

    public void compile(String[] args) throws Exception {
        File file = new File(args[0]);
        String fileName = file.getName();
        String path = file.getAbsolutePath();
        String className = fileName.replace(".hd", "");

        CompilationUnit compilationUnit = new Parser().getCompilationUnit(path);

        byte[] byteCode = new ByteCodeGenerator().generateByteCode(compilationUnit, className);
        saveByteCodeToClassFile(fileName, byteCode);
    }

    private void saveByteCodeToClassFile(String fileName, byte[] byteCode) throws IOException {
        String classFile = fileName.replace(".hd", ".class");
        try (OutputStream os = new FileOutputStream(classFile)) {
            os.write(byteCode);
        }
    }

}
