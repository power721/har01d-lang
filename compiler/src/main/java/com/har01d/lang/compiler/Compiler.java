package com.har01d.lang.compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.har01d.lang.compiler.domain.ClassDeclaration;
import com.har01d.lang.compiler.domain.CompilationUnit;
import com.har01d.lang.compiler.exception.InvalidSyntaxException;
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
                if (arg.endsWith("*.hd")) {
                    File dir = new File(arg.substring(0, arg.length() - 4));
                    if (dir.isDirectory()) {
                        File[] flist = dir.listFiles((dir1, name) -> name.endsWith(".hd"));
                        if (flist != null) {
                            files.addAll(Arrays.asList(flist));
                        }
                    }
                } else {
                    files.add(new File(arg));
                }
            }
        }

        if (!directory.exists()) {
            directory.mkdirs();
        }
        for (File file : files) {
            try {
                compiler.compile(directory, file);
            } catch (InvalidSyntaxException e) {
                System.err.println("Compile " + file + " failed at " + e.getLine() + ":" + e.getPosition() + ", error: "
                                                + e.getMessage());
            }
        }
    }

    public void compile(File directory, File file) throws Exception {
        CompilationUnit compilationUnit = new Parser().getCompilationUnit(file);

        ByteCodeGenerator generator = new ByteCodeGenerator();
        byte[] byteCode = generator.generateByteCode(compilationUnit, compilationUnit.getName());
        saveByteCodeToClassFile(directory, compilationUnit.getName(), byteCode);

        for (ClassDeclaration classDeclaration : compilationUnit.getClassDeclarations()) {
            byteCode = generator.generateByteCode(classDeclaration);
            saveByteCodeToClassFile(directory, classDeclaration.getName(), byteCode);
        }
    }

    private void saveByteCodeToClassFile(File directory, String className, byte[] byteCode) throws IOException {
        String classFile = className + ".class";
        File file = new File(directory, classFile);

        try (OutputStream os = new FileOutputStream(file)) {
            os.write(byteCode);
        }
    }

}
