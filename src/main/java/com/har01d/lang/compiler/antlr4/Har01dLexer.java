package com.har01d.lang.compiler.antlr4;// Generated from Har01d.g4 by ANTLR 4.7.1

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class Har01dLexer extends Lexer {
    public static final int VARIABLE = 1, PRINT = 2, EQUALS = 3, NUMBER = 4, BOOL = 5, STRING = 6, ID = 7, WS = 8;
    public static final String[] ruleNames = {"VARIABLE", "PRINT", "EQUALS", "NUMBER", "BOOL", "STRING", "ID", "WS"};
    /**
     * @deprecated Use {@link #VOCABULARY} instead.
     */
    @Deprecated
    public static final String[] tokenNames;
    public static final String _serializedATN = "\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\nO\b\1\4\2\t\2\4"
                                    + "\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\3\2\3\2\3\2\3\2"
                                    + "\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\6\5!\n\5\r\5\16\5\"\3\6\3\6\3\6\3"
                                    + "\6\3\6\3\6\3\6\3\6\3\6\5\6.\n\6\3\7\3\7\7\7\62\n\7\f\7\16\7\65\13\7\3"
                                    + "\7\3\7\3\7\7\7:\n\7\f\7\16\7=\13\7\3\7\5\7@\n\7\3\b\3\b\7\bD\n\b\f\b\16"
                                    + "\bG\13\b\3\t\6\tJ\n\t\r\t\16\tK\3\t\3\t\4\63;\2\n\3\3\5\4\7\5\t\6\13\7"
                                    + "\r\b\17\t\21\n\3\2\6\3\2\62;\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17"
                                    + "\"\"\2U\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2"
                                    + "\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\3\23\3\2\2\2\5\27\3\2\2\2\7\35\3"
                                    + "\2\2\2\t \3\2\2\2\13-\3\2\2\2\r?\3\2\2\2\17A\3\2\2\2\21I\3\2\2\2\23\24"
                                    + "\7x\2\2\24\25\7c\2\2\25\26\7t\2\2\26\4\3\2\2\2\27\30\7r\2\2\30\31\7t\2"
                                    + "\2\31\32\7k\2\2\32\33\7p\2\2\33\34\7v\2\2\34\6\3\2\2\2\35\36\7?\2\2\36"
                                    + "\b\3\2\2\2\37!\t\2\2\2 \37\3\2\2\2!\"\3\2\2\2\" \3\2\2\2\"#\3\2\2\2#\n"
                                    + "\3\2\2\2$%\7v\2\2%&\7t\2\2&\'\7w\2\2\'.\7g\2\2()\7h\2\2)*\7c\2\2*+\7n"
                                    + "\2\2+,\7u\2\2,.\7g\2\2-$\3\2\2\2-(\3\2\2\2.\f\3\2\2\2/\63\7$\2\2\60\62"
                                    + "\13\2\2\2\61\60\3\2\2\2\62\65\3\2\2\2\63\64\3\2\2\2\63\61\3\2\2\2\64\66"
                                    + "\3\2\2\2\65\63\3\2\2\2\66@\7$\2\2\67;\7)\2\28:\13\2\2\298\3\2\2\2:=\3"
                                    + "\2\2\2;<\3\2\2\2;9\3\2\2\2<>\3\2\2\2=;\3\2\2\2>@\7)\2\2?/\3\2\2\2?\67"
                                    + "\3\2\2\2@\16\3\2\2\2AE\t\3\2\2BD\t\4\2\2CB\3\2\2\2DG\3\2\2\2EC\3\2\2\2"
                                    + "EF\3\2\2\2F\20\3\2\2\2GE\3\2\2\2HJ\t\5\2\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2"
                                    + "\2KL\3\2\2\2LM\3\2\2\2MN\b\t\2\2N\22\3\2\2\2\n\2\"-\63;?EK\3\b\2\2";
    public static final ATN _ATN = new ATNDeserializer().deserialize(_serializedATN.toCharArray());
    protected static final DFA[] _decisionToDFA;
    protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
    private static final String[] _LITERAL_NAMES = {null, "'var'", "'print'", "'='"};
    private static final String[] _SYMBOLIC_NAMES =
                                    {null, "VARIABLE", "PRINT", "EQUALS", "NUMBER", "BOOL", "STRING", "ID", "WS"};
    public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);
    public static String[] channelNames = {"DEFAULT_TOKEN_CHANNEL", "HIDDEN"};
    public static String[] modeNames = {"DEFAULT_MODE"};

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

    public Har01dLexer(CharStream input) {
        super(input);
        _interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
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
    public String[] getChannelNames() {
        return channelNames;
    }

    @Override
    public String[] getModeNames() {
        return modeNames;
    }

    @Override
    public ATN getATN() {
        return _ATN;
    }
}