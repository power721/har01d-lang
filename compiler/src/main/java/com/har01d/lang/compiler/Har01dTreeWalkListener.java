package com.har01d.lang.compiler;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.har01d.lang.antlr.Har01dBaseListener;
import com.har01d.lang.antlr.Har01dParser;
import com.har01d.lang.compiler.asm.AssignVariable;
import com.har01d.lang.compiler.asm.Instruction;
import com.har01d.lang.compiler.asm.PrintValue;
import com.har01d.lang.compiler.asm.PrintVariable;
import com.har01d.lang.compiler.asm.VariableDeclaration;
import com.har01d.lang.compiler.domain.Value;
import com.har01d.lang.compiler.domain.Variable;

public class Har01dTreeWalkListener extends Har01dBaseListener {
    private Queue<Instruction> instructions = new ArrayDeque<>();
    private Map<String, Variable> variables = new HashMap<>();

    public Queue<Instruction> getInstructions() {
        return instructions;
    }

    @Override
    public void exitVariable(Har01dParser.VariableContext ctx) {
        TerminalNode varName = ctx.name().ID();
        Har01dParser.ValueContext varValue = ctx.value();
        int varType = varValue.getStart().getType();
        int varIndex = variables.size();
        String varTextValue = fixString(varType, varValue.getText());

        Variable var = new Variable(varIndex, varType, varTextValue);
        variables.put(varName.getText(), var);
        instructions.add(new VariableDeclaration(var));
    }

    @Override
    public void exitAssign(Har01dParser.AssignContext ctx) {
        TerminalNode varName = ctx.name().ID();
        Har01dParser.ValueContext varValue = ctx.value();

        if (!variables.containsKey(varName.getText())) {
            System.err.printf("var '%s' has not been declared!", varName.getText());
            return;
        }

        Variable var = variables.get(varName.getText());
        String varTextValue = fixString(var.getType(), varValue.getText());
        instructions.add(new AssignVariable(var, varTextValue));
    }

    @Override
    public void exitPrint(Har01dParser.PrintContext ctx) {
        Har01dParser.ExpressionContext expressionContext = ctx.expression();
        TerminalNode varName = expressionContext.ID();

        if (varName == null) {
            Har01dParser.ValueContext valueContext = expressionContext.value();
            int varType = valueContext.getStart().getType();
            String varTextValue = fixString(varType, valueContext.getText());
            Value value = new Value(varType, varTextValue);
            instructions.add(new PrintValue(value));
            return;
        }

        if (!variables.containsKey(varName.getText())) {
            System.err.printf("var '%s' has not been declared!", varName.getText());
            return;
        }

        Variable variable = variables.get(varName.getText());
        instructions.add(new PrintVariable(variable));
    }

    private String fixString(int type, String value) {
        if (type == Har01dParser.STRING && value.charAt(0) == '\'' && value.charAt(value.length() - 1) == '\'') {
            return "\"" + value.substring(1, value.length() - 1) + "\"";
        }
        return value;
    }

}
