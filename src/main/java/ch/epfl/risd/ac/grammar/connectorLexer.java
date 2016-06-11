// Generated from connector.g4 by ANTLR 4.0

package ch.epfl.risd.ac.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNSimulator;
import org.antlr.v4.runtime.atn.LexerATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;

import ch.epfl.risd.ac.model.ConnectorNode;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class connectorLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int LPAREN = 1, RPAREN = 2, ID = 3, TRIGGER = 4, WS = 5;
	public static String[] modeNames = { "DEFAULT_MODE" };

	public static final String[] tokenNames = { "<INVALID>", "'['", "']'", "ID", "'''", "WS" };
	public static final String[] ruleNames = { "LPAREN", "RPAREN", "ID", "TRIGGER", "WS" };

	public List<String> sports = new ArrayList<String>();
	public List<String> tports = new ArrayList<String>();

	public ConnectorNode newList() {
		ConnectorNode s = new ConnectorNode(false, new ArrayList<String>(sports));
		if (tports.isEmpty())
			return s;
		List<ConnectorNode> lst = new ArrayList<ConnectorNode>();
		lst.add(s);
		for (String str : tports) {
			List<String> strlst = new ArrayList<String>();
			strlst.add(str);
			lst.add(new ConnectorNode(true, strlst));
		}
		ConnectorNode top = new ConnectorNode(false, null);
		for (ConnectorNode c : lst)
			c.attachToNode(top);
		return top;
	}

	public ConnectorNode newNode(boolean istrig, List<ConnectorNode> l) {
		ConnectorNode n = new ConnectorNode(istrig, null);
		for (ConnectorNode cn : l)
			cn.attachToNode(n);
		return n;
	}

	public connectorLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	@Override
	public String getGrammarFileName() {
		return "connector.g4";
	}

	@Override
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String[] getModeNames() {
		return modeNames;
	}

	@Override
	public ATN getATN() {
		return _ATN;
	}

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 4:
			WS_action((RuleContext) _localctx, actionIndex);
			break;
		}
	}

	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			skip();
			break;
		}
	}

	public static final String _serializedATN = "\2\4\7!\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\3\3\3\3"
			+ "\4\3\4\7\4\24\n\4\f\4\16\4\27\13\4\3\5\3\5\3\6\6\6\34\n\6\r\6\16\6\35"
			+ "\3\6\3\6\2\7\3\3\1\5\4\1\7\5\1\t\6\1\13\7\2\3\2\5\6\60\60C\\aac|\7\60"
			+ "\60\62;C\\aac|\5\13\f\17\17\"\"\"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2"
			+ "\2\t\3\2\2\2\2\13\3\2\2\2\3\r\3\2\2\2\5\17\3\2\2\2\7\21\3\2\2\2\t\30\3"
			+ "\2\2\2\13\33\3\2\2\2\r\16\7]\2\2\16\4\3\2\2\2\17\20\7_\2\2\20\6\3\2\2"
			+ "\2\21\25\t\2\2\2\22\24\t\3\2\2\23\22\3\2\2\2\24\27\3\2\2\2\25\23\3\2\2"
			+ "\2\25\26\3\2\2\2\26\b\3\2\2\2\27\25\3\2\2\2\30\31\7)\2\2\31\n\3\2\2\2"
			+ "\32\34\t\4\2\2\33\32\3\2\2\2\34\35\3\2\2\2\35\33\3\2\2\2\35\36\3\2\2\2"
			+ "\36\37\3\2\2\2\37 \b\6\2\2 \f\3\2\2\2\5\2\25\35";
	public static final ATN _ATN = ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}