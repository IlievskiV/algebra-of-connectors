// Generated from connector.g4 by ANTLR 4.0

package ch.epfl.risd.ac.grammar;

import org.antlr.v4.runtime.tree.ParseTreeListener;

public interface ConnectorListener extends ParseTreeListener {

	void enterIdent(ConnectorParser.IdentContext ctx);

	void exitIdent(ConnectorParser.IdentContext ctx);

	void enterFormulainner(ConnectorParser.FormulainnerContext ctx);

	void exitFormulainner(ConnectorParser.FormulainnerContext ctx);

	void enterIdentlist(ConnectorParser.IdentlistContext ctx);

	void exitIdentlist(ConnectorParser.IdentlistContext ctx);

	void enterTop(ConnectorParser.TopContext ctx);

	void exitTop(ConnectorParser.TopContext ctx);

	void enterFormula(ConnectorParser.FormulaContext ctx);

	void exitFormula(ConnectorParser.FormulaContext ctx);
}