package com.har01d.lang.compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.generator.ByteCodeGenerator;
import com.har01d.lang.compiler.parser.Parser;

public class Compiler {

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: java com.har01d.lang.compiler.Compiler <FILE_NAME>.hd");
            System.exit(1);
        }

        Compiler compiler = new Compiler();
        File directory = new File(".");
        List<File> files = new ArrayList<>();
        for (int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if (arg.equals("-d") || arg.equals("--directory")) {
                if (i + 1 < args.length) {
                    directory = new File(args[i + 1]);
                    i++;
                }
            } else {
                files.add(new File(arg));
            }
        }

        if (!directory.exists()) {
            directory.mkdirs();
        }
        for (File file : files) {
            compiler.compile(directory, file);
        }
    }

    public void compile(File directory, File file) throws Exception {
        String fileName = file.getName();
        String path = file.getAbsolutePath();
        String className = getClassName(fileName);

        CompilationUnit compilationUnit = new Parser().getCompilationUnit(path);

        byte[] byteCode = new ByteCodeGenerator().generateByteCode(compilationUnit, className);
        saveByteCodeToClassFile(directory, className, byteCode);
    }

    private String getClassName(String fileName) {
        if (fileName.endsWith(".hd")) {
            return fileName.substring(0, fileName.length() - 3);
        }
        return fileName;
    }

    private void saveByteCodeToClassFile(File directory, String className, byte[] byteCode) throws IOException {
        String classFile = className + ".class";
        File file = new File(directory, classFile);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(byteCode);
        }
    }

}
