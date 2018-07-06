package com.har01d.lang.compiler.visitor;

import com.har01d.lang.antlr.Har01dBaseVisitor;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.domain.ClassDeclaration;
import com.har01d.lang.compiler.domain.MetaData;
import com.har01d.lang.compiler.domain.Scope;
import com.har01d.lang.compiler.domain.function.Constructor;
import com.har01d.lang.compiler.domain.function.Function;
import com.har01d.lang.compiler.domain.function.FunctionSignature;
import com.har01d.lang.compiler.domain.statement.Block;
import com.har01d.lang.compiler.domain.statement.Statement;
import com.har01d.lang.compiler.domain.type.BuiltInType;
import com.har01d.lang.compiler.domain.type.Type;
import com.har01d.lang.compiler.util.FunctionUtil;
import java.util.ArrayList;
import java.util.List;
import org.objectweb.asm.Opcodes;

public class ClassVisitor extends Har01dBaseVisitor<ClassDeclaration> {

    @Override
    public ClassDeclaration visitClassDeclaration(Har01dParser.ClassDeclarationContext ctx) {
        String className = ctx.className().getText();
        Scope scope = new Scope(new MetaData(className, true));
        List<Function> functions = FunctionUtil.getFunctions(ctx.classBody().function(), scope);
        List<Function> constructors = FunctionUtil.getConstructors(ctx.classBody().constructor(), scope);

        if (constructors.isEmpty()) {
            String name = scope.getClassName();
            Type type = BuiltInType.VOID;
            int flag = Opcodes.ACC_PUBLIC;
            FunctionSignature signature = new FunctionSignature(name, "<init>", new ArrayList<>(), flag, type);
            Statement block = Block.empty(scope);
            constructors.add(new Constructor(signature, block));
        }

        return new ClassDeclaration(className, constructors, functions);
    }

}
