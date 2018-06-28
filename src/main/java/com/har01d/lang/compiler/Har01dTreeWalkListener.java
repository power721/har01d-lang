package com.har01d.lang.compiler;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.har01d.lang.compiler.antlr4.Har01dBaseListener;
import com.har01d.lang.compiler.antlr4.Har01dParser;
import com.har01d.lang.compiler.asm.Instruction;
import com.har01d.lang.compiler.asm.PrintVariable;
import com.har01d.lang.compiler.asm.VariableDeclaration;
import com.har01d.lang.compiler.domain.Variable;

public class Har01dTreeWalkListener extends Har01dBaseListener {
    private Queue<Instruction> instructions = new ArrayDeque<>();
    private Map<String, Variable> variables = new HashMap<>();

    public Queue<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public void exitVariable(Har01dParser.VariableContext ctx) {
        TerminalNode varName = ctx.ID();
        Har01dParser.ValueContext varValue = ctx.value();
        int varType = varValue.getStart().getType();
        int varIndex = variables.size();
        String varTextValue = varValue.getText();

        Variable var = new Variable(varIndex, varType, varTextValue);
        variables.put(varName.getText(), var);
        instructions.add(new VariableDeclaration(var));
    }

    @Override
    public void exitPrint(Har01dParser.PrintContext ctx) {
        TerminalNode varName = ctx.ID();
        if (!variables.containsKey(varName.getText())) {
            System.err.printf("var '%s' has not been declared!", varName.getText());
            return;
        }

        Variable variable = variables.get(varName.getText());
        instructions.add(new PrintVariable(variable));
    }
}
