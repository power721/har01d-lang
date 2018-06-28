package com.har01d.lang.compiler.antlr4;// Generated from Har01d.g4 by ANTLR 4.7.1

import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Har01dParser extends Parser {
    public static final int VARIABLE = 1, PRINT = 2, EQUALS = 3, NUMBER = 4, STRING = 5, ID = 6, WS = 7;
    public static final int RULE_compilationUnit = 0, RULE_variable = 1, RULE_assign = 2, RULE_print = 3,
                                    RULE_value = 4;
    public static final String[] ruleNames = {"compilationUnit", "variable", "assign", "print", "value"};
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN = "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\t%\4\2\t\2\4\3\t"
                                    + "\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\7\2\20\n\2\f\2\16\2\23\13\2\3\2"
                                    + "\3\2\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\2\2\7"
                                    + "\2\4\6\b\n\2\3\3\2\6\7\2\"\2\21\3\2\2\2\4\26\3\2\2\2\6\33\3\2\2\2\b\37"
                                    + "\3\2\2\2\n\"\3\2\2\2\f\20\5\4\3\2\r\20\5\6\4\2\16\20\5\b\5\2\17\f\3\2"
                                    + "\2\2\17\r\3\2\2\2\17\16\3\2\2\2\20\23\3\2\2\2\21\17\3\2\2\2\21\22\3\2"
                                    + "\2\2\22\24\3\2\2\2\23\21\3\2\2\2\24\25\7\2\2\3\25\3\3\2\2\2\26\27\7\3"
                                    + "\2\2\27\30\7\b\2\2\30\31\7\5\2\2\31\32\5\n\6\2\32\5\3\2\2\2\33\34\7\b"
                                    + "\2\2\34\35\7\5\2\2\35\36\5\n\6\2\36\7\3\2\2\2\37 \7\4\2\2 !\7\b\2\2!\t"
                                    + "\3\2\2\2\"#\t\2\2\2#\13\3\2\2\2\4\17\21";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {null, "'var'", "'print'", "'='"};
    private static final String[] _SYMBOLIC_NAMES =
                                    {null, "VARIABLE", "PRINT", "EQUALS", "NUMBER", "STRING", "ID", "WS"};
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

    static {
        RuntimeMetaData.checkVersion("4.7.1", RuntimeMetaData.VERSION);
    }

    static {
        tokenNames = new String[_SYMBOLIC_NAMES.length];
        for (int i = 0; i < tokenNames.length; i++) {
            tokenNames[i] = VOCABULARY.getLiteralName(i);
            if (tokenNames[i] == null) {
                tokenNames[i] = VOCABULARY.getSymbolicName(i);
            }

            if (tokenNames[i] == null) {
                tokenNames[i] = "<INVALID>";
            }
        }
    }

    static {
        _decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
        for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
            _decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
        }
    }

    public Har01dParser(TokenStream input) {
        super(input);
        _interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
    }

    @Override
    @Deprecated
    public String[] getTokenNames() {
        return tokenNames;
    }

    @Override

    public Vocabulary getVocabulary() {
        return VOCABULARY;
    }

    @Override
    public String getGrammarFileName() {
        return "Har01d.g4";
    }

    @Override
    public String[] getRuleNames() {
        return ruleNames;
    }

    @Override
    public String getSerializedATN() {
        return _serializedATN;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }

    public final CompilationUnitContext compilationUnit() throws RecognitionException {
        CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
        enterRule(_localctx, 0, RULE_compilationUnit);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(15);
                _errHandler.sync(this);
                _la = _input.LA(1);
                while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VARIABLE) | (1L << PRINT) | (1L << ID))) != 0)) {
                    {
                        setState(13);
                        _errHandler.sync(this);
                        switch (_input.LA(1)) {
                            case VARIABLE: {
                                setState(10);
                                variable();
                            }
                                break;
                            case ID: {
                                setState(11);
                                assign();
                            }
                                break;
                            case PRINT: {
                                setState(12);
                                print();
                            }
                                break;
                            default:
                                throw new NoViableAltException(this);
                        }
                    }
                    setState(17);
                    _errHandler.sync(this);
                    _la = _input.LA(1);
                }
                setState(18);
                match(EOF);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final VariableContext variable() throws RecognitionException {
        VariableContext _localctx = new VariableContext(_ctx, getState());
        enterRule(_localctx, 2, RULE_variable);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(20);
                match(VARIABLE);
                setState(21);
                match(ID);
                setState(22);
                match(EQUALS);
                setState(23);
                value();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final AssignContext assign() throws RecognitionException {
        AssignContext _localctx = new AssignContext(_ctx, getState());
        enterRule(_localctx, 4, RULE_assign);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(25);
                match(ID);
                setState(26);
                match(EQUALS);
                setState(27);
                value();
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final PrintContext print() throws RecognitionException {
        PrintContext _localctx = new PrintContext(_ctx, getState());
        enterRule(_localctx, 6, RULE_print);
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(29);
                match(PRINT);
                setState(30);
                match(ID);
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public final ValueContext value() throws RecognitionException {
        ValueContext _localctx = new ValueContext(_ctx, getState());
        enterRule(_localctx, 8, RULE_value);
        int _la;
        try {
            enterOuterAlt(_localctx, 1);
            {
                setState(32);
                _la = _input.LA(1);
                if (!(_la == NUMBER || _la == STRING)) {
                    _errHandler.recoverInline(this);
                } else {
                    if (_input.LA(1) == Token.EOF)
                        matchedEOF = true;
                    _errHandler.reportMatch(this);
                    consume();
                }
            }
        } catch (RecognitionException re) {
            _localctx.exception = re;
            _errHandler.reportError(this, re);
            _errHandler.recover(this, re);
        } finally {
            exitRule();
        }
        return _localctx;
    }

    public static class VariableContext extends ParserRuleContext {
        public VariableContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode VARIABLE() {
            return getToken(Har01dParser.VARIABLE, 0);
        }

        public TerminalNode ID() {
            return getToken(Har01dParser.ID, 0);
        }

        public TerminalNode EQUALS() {
            return getToken(Har01dParser.EQUALS, 0);
        }

        public ValueContext value() {
            return getRuleContext(ValueContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_variable;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).enterVariable(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).exitVariable(this);
        }
    }

    public static class PrintContext extends ParserRuleContext {
        public PrintContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode PRINT() {
            return getToken(Har01dParser.PRINT, 0);
        }

        public TerminalNode ID() {
            return getToken(Har01dParser.ID, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_print;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).enterPrint(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).exitPrint(this);
        }
    }

    public static class ValueContext extends ParserRuleContext {
        public ValueContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode NUMBER() {
            return getToken(Har01dParser.NUMBER, 0);
        }

        public TerminalNode STRING() {
            return getToken(Har01dParser.STRING, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_value;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).enterValue(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).exitValue(this);
        }
    }

    public static class CompilationUnitContext extends ParserRuleContext {
        public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode EOF() {
            return getToken(Har01dParser.EOF, 0);
        }

        public List<VariableContext> variable() {
            return getRuleContexts(VariableContext.class);
        }

        public VariableContext variable(int i) {
            return getRuleContext(VariableContext.class, i);
        }

        public List<AssignContext> assign() {
            return getRuleContexts(AssignContext.class);
        }

        public AssignContext assign(int i) {
            return getRuleContext(AssignContext.class, i);
        }

        public List<PrintContext> print() {
            return getRuleContexts(PrintContext.class);
        }

        public PrintContext print(int i) {
            return getRuleContext(PrintContext.class, i);
        }

        @Override
        public int getRuleIndex() {
            return RULE_compilationUnit;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).enterCompilationUnit(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).exitCompilationUnit(this);
        }
    }

    public static class AssignContext extends ParserRuleContext {
        public AssignContext(ParserRuleContext parent, int invokingState) {
            super(parent, invokingState);
        }

        public TerminalNode ID() {
            return getToken(Har01dParser.ID, 0);
        }

        public TerminalNode EQUALS() {
            return getToken(Har01dParser.EQUALS, 0);
        }

        public ValueContext value() {
            return getRuleContext(ValueContext.class, 0);
        }

        @Override
        public int getRuleIndex() {
            return RULE_assign;
        }

        @Override
        public void enterRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).enterAssign(this);
        }

        @Override
        public void exitRule(ParseTreeListener listener) {
            if (listener instanceof Har01dListener)
                ((Har01dListener) listener).exitAssign(this);
        }
    }
}
