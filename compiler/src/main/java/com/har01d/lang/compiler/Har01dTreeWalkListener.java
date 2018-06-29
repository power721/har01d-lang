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
import com.har01d.lang.compiler.asm.PrintLiteral;
import com.har01d.lang.compiler.asm.PrintVariable;
import com.har01d.lang.compiler.asm.VariableDeclaration;
import com.har01d.lang.compiler.domain.Literal;
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
        if (variables.containsKey(varName.getText())) {
            System.err.printf("variable '%s' already declared!", varName.getText());
            System.exit(1);
        }

        Har01dParser.LiteralContext varValue = ctx.literal();
        int varType = varValue.getStart().getType();
        int varIndex = variables.size();
        String varTextValue = fixString(varType, varValue.getText());

        Variable var = new Variable(varIndex, varType, varTextValue);
        variables.put(varName.getText(), var);
        instructions.add(new VariableDeclaration(var));
    }

    @Override
    public void exitValue(Har01dParser.ValueContext ctx) {
        TerminalNode valName = ctx.name().ID();
        if (variables.containsKey(valName.getText())) {
            System.err.printf("variable '%s' already declared!", valName.getText());
            System.exit(1);
        }

        Har01dParser.LiteralContext valValue = ctx.literal();
        int valType = valValue.getStart().getType();
        int valIndex = variables.size();
        String valTextValue = fixString(valType, valValue.getText());

        Value val = new Value(valIndex, valType, valTextValue);
        variables.put(valName.getText(), val);
        instructions.add(new VariableDeclaration(val));
    }

    @Override
    public void exitAssign(Har01dParser.AssignContext ctx) {
        TerminalNode varName = ctx.name().ID();
        Har01dParser.LiteralContext varValue = ctx.literal();

        if (!variables.containsKey(varName.getText())) {
            System.err.printf("variable '%s' has not been declared!", varName.getText());
            System.exit(1);
        }

        Variable var = variables.get(varName.getText());
        if (var instanceof Value) {
            System.err.printf("variable '%s' cannot change value!", varName.getText());
            System.exit(1);
        }

        String varTextValue = fixString(var.getType(), varValue.getText());
        instructions.add(new AssignVariable(var, varTextValue));
    }

    @Override
    public void exitPrint(Har01dParser.PrintContext ctx) {
        Har01dParser.ExpressionContext expressionContext = ctx.expression();
        TerminalNode varName = expressionContext.ID();

        if (varName == null) {
            Har01dParser.LiteralContext valueContext = expressionContext.literal();
            int varType = valueContext.getStart().getType();
            String varTextValue = fixString(varType, valueContext.getText());
            Literal literal = new Literal(varType, varTextValue);
            instructions.add(new PrintLiteral(literal));
            return;
        }

        if (!variables.containsKey(varName.getText())) {
            System.err.printf("variable '%s' has not been declared!", varName.getText());
            System.exit(1);
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
