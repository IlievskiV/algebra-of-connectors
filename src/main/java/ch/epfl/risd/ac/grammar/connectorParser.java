// Generated from connector.g4 by ANTLR 4.0

package ch.epfl.risd.ac.grammar;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNSimulator;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import ch.epfl.risd.ac.model.ConnectorNode;

@SuppressWarnings({ "all", "warnings", "unchecked", "unused", "cast" })
public class connectorParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache = new PredictionContextCache();
	public static final int LPAREN = 1, RPAREN = 2, ID = 3, TRIGGER = 4, WS = 5;
	public static final String[] tokenNames = { "<INVALID>", "'['", "']'", "ID", "'''", "WS" };
	public static final int RULE_ident = 0, RULE_identlist = 1, RULE_formulainner = 2, RULE_formula = 3, RULE_top = 4;
	public static final String[] ruleNames = { "ident", "identlist", "formulainner", "formula", "top" };

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
	public ATN getATN() {
		return _ATN;
	}

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

	public connectorParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this, _ATN, _decisionToDFA, _sharedContextCache);
	}

	public static class IdentContext extends ParserRuleContext {
		public Token var;

		public TerminalNode ID() {
			return getToken(connectorParser.ID, 0);
		}

		public TerminalNode TRIGGER() {
			return getToken(connectorParser.TRIGGER, 0);
		}

		public IdentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_ident;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).enterIdent(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).exitIdent(this);
		}
	}

	public final IdentContext ident() throws RecognitionException {
		IdentContext _localctx = new IdentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_ident);
		try {
			setState(15);
			switch (getInterpreter().adaptivePredict(_input, 0, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(10);
				((IdentContext) _localctx).var = match(ID);
				sports.add((((IdentContext) _localctx).var != null ? ((IdentContext) _localctx).var.getText() : null));
			}
				break;

			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(12);
				((IdentContext) _localctx).var = match(ID);
				setState(13);
				match(TRIGGER);
				tports.add((((IdentContext) _localctx).var != null ? ((IdentContext) _localctx).var.getText() : null));
			}
				break;
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

	public static class IdentlistContext extends ParserRuleContext {
		public ConnectorNode c;

		public List<IdentContext> ident() {
			return getRuleContexts(IdentContext.class);
		}

		public IdentContext ident(int i) {
			return getRuleContext(IdentContext.class, i);
		}

		public IdentlistContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_identlist;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).enterIdentlist(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).exitIdentlist(this);
		}
	}

	public final IdentlistContext identlist() throws RecognitionException {
		IdentlistContext _localctx = new IdentlistContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_identlist);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
				setState(18);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input, 1, _ctx);
				do {
					switch (_alt) {
					case 1: {
						{
							setState(17);
							ident();
						}
					}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(20);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input, 1, _ctx);
				} while (_alt != 2 && _alt != -1);

				((IdentlistContext) _localctx).c = newList();
				sports.clear();
				tports.clear();
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

	public static class FormulainnerContext extends ParserRuleContext {
		public ConnectorNode c;
		public FormulaContext f;
		public FormulaContext formula;

		public TerminalNode RPAREN() {
			return getToken(connectorParser.RPAREN, 0);
		}

		public TerminalNode LPAREN() {
			return getToken(connectorParser.LPAREN, 0);
		}

		public TerminalNode TRIGGER() {
			return getToken(connectorParser.TRIGGER, 0);
		}

		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class, 0);
		}

		public FormulainnerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_formulainner;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).enterFormulainner(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).exitFormulainner(this);
		}
	}

	public final FormulainnerContext formulainner() throws RecognitionException {
		FormulainnerContext _localctx = new FormulainnerContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_formulainner);
		try {
			setState(35);
			switch (getInterpreter().adaptivePredict(_input, 2, _ctx)) {
			case 1:
				enterOuterAlt(_localctx, 1); {
				setState(24);
				match(LPAREN);
				setState(25);
				((FormulainnerContext) _localctx).f = ((FormulainnerContext) _localctx).formula = formula(
						new ArrayList<ConnectorNode>());
				setState(26);
				match(RPAREN);
				((FormulainnerContext) _localctx).c = newNode(false, ((FormulainnerContext) _localctx).formula.listOut);
			}
				break;

			case 2:
				enterOuterAlt(_localctx, 2); {
				setState(29);
				match(LPAREN);
				setState(30);
				((FormulainnerContext) _localctx).f = ((FormulainnerContext) _localctx).formula = formula(
						new ArrayList<ConnectorNode>());
				setState(31);
				match(RPAREN);
				setState(32);
				match(TRIGGER);
				((FormulainnerContext) _localctx).c = newNode(true, ((FormulainnerContext) _localctx).formula.listOut);
			}
				break;
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

	public static class FormulaContext extends ParserRuleContext {
		public List<ConnectorNode> listIn;
		public List<ConnectorNode> listOut;
		public IdentlistContext i;
		public FormulainnerContext f;

		public IdentlistContext identlist(int i) {
			return getRuleContext(IdentlistContext.class, i);
		}

		public List<FormulainnerContext> formulainner() {
			return getRuleContexts(FormulainnerContext.class);
		}

		public List<IdentlistContext> identlist() {
			return getRuleContexts(IdentlistContext.class);
		}

		public FormulainnerContext formulainner(int i) {
			return getRuleContext(FormulainnerContext.class, i);
		}

		public FormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		public FormulaContext(ParserRuleContext parent, int invokingState, List<ConnectorNode> listIn) {
			super(parent, invokingState);
			this.listIn = listIn;
		}

		@Override
		public int getRuleIndex() {
			return RULE_formula;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).enterFormula(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).exitFormula(this);
		}
	}

	public final FormulaContext formula(List<ConnectorNode> listIn) throws RecognitionException {
		FormulaContext _localctx = new FormulaContext(_ctx, getState(), listIn);
		enterRule(_localctx, 6, RULE_formula);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
						setState(43);
						switch (_input.LA(1)) {
						case ID: {
							setState(37);
							((FormulaContext) _localctx).i = identlist();
							_localctx.listIn.add(((FormulaContext) _localctx).i.c);
						}
							break;
						case LPAREN: {
							setState(40);
							((FormulaContext) _localctx).f = formulainner();
							_localctx.listIn.add(((FormulaContext) _localctx).f.c);
						}
							break;
						default:
							throw new NoViableAltException(this);
						}
					}
					setState(45);
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while (_la == LPAREN || _la == ID);
				((FormulaContext) _localctx).listOut = _localctx.listIn;
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

	public static class TopContext extends ParserRuleContext {
		public ConnectorNode c;
		public FormulaContext formula;

		public FormulaContext formula() {
			return getRuleContext(FormulaContext.class, 0);
		}

		public TopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}

		@Override
		public int getRuleIndex() {
			return RULE_top;
		}

		@Override
		public void enterRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).enterTop(this);
		}

		@Override
		public void exitRule(ParseTreeListener listener) {
			if (listener instanceof connectorListener)
				((connectorListener) listener).exitTop(this);
		}
	}

	public final TopContext top() throws RecognitionException {
		TopContext _localctx = new TopContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_top);
		try {
			enterOuterAlt(_localctx, 1);
			{
				setState(49);
				((TopContext) _localctx).formula = formula(new ArrayList<ConnectorNode>());
				((TopContext) _localctx).c = newNode(false, ((TopContext) _localctx).formula.listOut);
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

	public static final String _serializedATN = "\2\3\7\67\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\3\2\3\2"
			+ "\5\2\22\n\2\3\3\6\3\25\n\3\r\3\16\3\26\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4"
			+ "\3\4\3\4\3\4\3\4\3\4\5\4&\n\4\3\5\3\5\3\5\3\5\3\5\3\5\6\5.\n\5\r\5\16"
			+ "\5/\3\5\3\5\3\6\3\6\3\6\3\6\2\7\2\4\6\b\n\2\2\66\2\21\3\2\2\2\4\24\3\2"
			+ "\2\2\6%\3\2\2\2\b-\3\2\2\2\n\63\3\2\2\2\f\r\7\5\2\2\r\22\b\2\1\2\16\17"
			+ "\7\5\2\2\17\20\7\6\2\2\20\22\b\2\1\2\21\f\3\2\2\2\21\16\3\2\2\2\22\3\3"
			+ "\2\2\2\23\25\5\2\2\2\24\23\3\2\2\2\25\26\3\2\2\2\26\24\3\2\2\2\26\27\3"
			+ "\2\2\2\27\30\3\2\2\2\30\31\b\3\1\2\31\5\3\2\2\2\32\33\7\3\2\2\33\34\5"
			+ "\b\5\2\34\35\7\4\2\2\35\36\b\4\1\2\36&\3\2\2\2\37 \7\3\2\2 !\5\b\5\2!"
			+ "\"\7\4\2\2\"#\7\6\2\2#$\b\4\1\2$&\3\2\2\2%\32\3\2\2\2%\37\3\2\2\2&\7\3"
			+ "\2\2\2\'(\5\4\3\2()\b\5\1\2).\3\2\2\2*+\5\6\4\2+,\b\5\1\2,.\3\2\2\2-\'"
			+ "\3\2\2\2-*\3\2\2\2./\3\2\2\2/-\3\2\2\2/\60\3\2\2\2\60\61\3\2\2\2\61\62"
			+ "\b\5\1\2\62\t\3\2\2\2\63\64\5\b\5\2\64\65\b\6\1\2\65\13\3\2\2\2\7\21\26" + "%-/";
	public static final ATN _ATN = ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}