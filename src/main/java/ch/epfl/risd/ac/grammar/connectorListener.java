// Generated from connector.g4 by ANTLR 4.0

package ch.epfl.risd.ac.grammar;

import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface connectorListener extends ParseTreeListener {
	void enterIdent(connectorParser.IdentContext ctx);

	void exitIdent(connectorParser.IdentContext ctx);

	void enterFormulainner(connectorParser.FormulainnerContext ctx);

	void exitFormulainner(connectorParser.FormulainnerContext ctx);

	void enterIdentlist(connectorParser.IdentlistContext ctx);

	void exitIdentlist(connectorParser.IdentlistContext ctx);

	void enterTop(connectorParser.TopContext ctx);

	void exitTop(connectorParser.TopContext ctx);

	void enterFormula(connectorParser.FormulaContext ctx);

	void exitFormula(connectorParser.FormulaContext ctx);
}