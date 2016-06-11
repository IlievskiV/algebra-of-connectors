package ch.epfl.risd.ac.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.commons.collections4.Transformer;

import ch.epfl.risd.ac.datastructures.Node;
import ch.epfl.risd.ac.datastructures.Tuple;
import ch.epfl.risd.ac.grammar.connectorLexer;
import ch.epfl.risd.ac.grammar.connectorParser;
import ch.epfl.risd.ac.helpers.Helpers;
import ch.epfl.risd.ac.helpers.PortChecker;

/**
 * This models one connector in the sense of the Algebra of Connectors, i.e. one
 * connector is set of communicating ports belonging to different components
 * that may be involved in some interactions.
 *
 */

public class Connector {

	// <isTrigger, Interaction>; Interaction != null iff it is a leaf
	public ConnectorNode root;

	public Set<String> ports;

	public Connector() {
		ports = new HashSet<String>();
		root = new ConnectorNode(false, null);
	}

	public Connector(Set<String> ports, ConnectorNode cn) {
		if (ports != null)
			this.ports = ports;
		else
			this.ports = new HashSet<String>();
		this.root = cn;
		checkPorts();
		System.err.println("Connector");
		System.err.println(print());
	}

	private void checkPorts() {
		Transformer tr = new Transformer() {
			public Object transform(Object arg0) {
				Tuple<Set<String>, ConnectorNode> input = Helpers.cast(arg0);
				if (input.snd().data.snd() != null)
					input.fst().addAll(input.snd().data.snd());
				return input.fst();
			}
		};
		PortChecker.checkPorts(root, tr, ports);
	}

	public Connector(CausalTree ct) {
		if (ct != null) {
			this.ports = ct.ports;
			root = fromCausalTree(ct);
			checkPorts();
		}
		System.err.println("Connector");
		System.err.println(print());

	}

	/**************************************************************************************
	 * Print
	 ***************************************************************************************/

	private String printTree(Node<Tuple<Boolean, List<String>>> node) {
		if (node == null)
			return "";

		if (node.data.y != null && node.getChildren().size() != 0) {
			System.err.println("Incorrect Connector");
			return "";
		}

		StringBuilder inner = new StringBuilder("[");

		if (node.data.y != null)
			inner.append(Helpers.listToString(node.data.y));

		else {
			for (int i = 0; i < node.getChildren().size(); i++)
				inner.append(printTree(node.getChildren().get(i)));
		}

		inner.append("]");
		if (node.data.x)
			inner.append("'");
		return inner.toString();
	}

	public String print() {
		if (this.root == null)
			return "";
		return printTree(root);
	}

	/**************************************************************************************
	 * Generate from Causal Tree
	 ***************************************************************************************/

	private void fromCausalTreeInner(Node<List<String>> node, ConnectorNode parentNode) {
		if (node.getChildren().size() == 0) {
			new ConnectorNode(false, node.data, parentNode);
			return;
		}
		new ConnectorNode(true, node.data, parentNode);
		if (node.getChildren().size() == 1 && node.getChildren().get(0).getChildren().size() == 0) {
			new ConnectorNode(false, node.getChildren().get(0).data, parentNode);
		} else {
			ConnectorNode synch = new ConnectorNode(false, null, parentNode);
			for (int i = 0; i < node.getChildren().size(); i++) {
				fromCausalTreeInner(node.getChildren().get(i), synch);
			}
		}
	}

	public ConnectorNode fromCausalTree(CausalTree causalTree) {
		ConnectorNode result = new ConnectorNode(false, null, null);
		if (causalTree.ctRoot.getChildren().size() == 1)
			fromCausalTreeInner(causalTree.ctRoot.getChildren().get(0), result);
		else {
			for (int i = 0; i < causalTree.ctRoot.getChildren().size(); i++) {
				Node<List<String>> child = causalTree.ctRoot.getChildren().get(i);
				if (child.getChildren().size() == 0)
					new ConnectorNode(true, child.getData(), result);
				else {
					ConnectorNode trigger = new ConnectorNode(true, null, result);
					fromCausalTreeInner(child, trigger);
				}
			}
		}
		return result;
	}

	/**************************************************************************************
	 * From String
	 ***************************************************************************************/

	public static ConnectorNode FromString(String input) {
		connectorLexer lexer = new connectorLexer(new ANTLRInputStream(input));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		connectorParser p = new connectorParser(tokens);
		p.setBuildParseTree(true);
		// p.addParseListener(new boolListener());
		return p.top().c;
	}

	/**************************************************************************************
	 * Generators
	 ***************************************************************************************/

	public CausalTree toCausalTree() {
		return new CausalTree(this);
	}

	public List<String> getInteractions(Node<Tuple<Boolean, List<String>>> node) {

		/* The resulting list */
		List<String> result = new LinkedList<String>();

		if (node.data.y != null) {
			/* Add all interactions */
			result.addAll(node.data.y);
		} else {
			/* Get the children of the node */
			ArrayList<Node<Tuple<Boolean, List<String>>>> children = node.getChildren();

			/* Iterate over them */
			for (int i = 0; i < children.size(); i++) {
				/* Add the results in the resulting list */
				result.addAll(getInteractions(children.get(i)));
			}
		}

		return result;
	}

	public static void main(String[] args) {
		/* The set of ports */
		Set<String> ports = new HashSet<String>();
		/* Add the ports */
		ports.add("p");
		ports.add("q");
		ports.add("r");
		ports.add("s");
		ports.add("t");

		/* Create Connector from the String representation of the interaction */
		ConnectorNode connectorNode = FromString("pq");
		/* Create the connector */
		Connector connector = new Connector(ports, connectorNode);

		/* Using the connector create a Causal Tree */
		CausalTree causalTree = connector.toCausalTree();

		List<String> interactions = causalTree.getInteractions();

		for (String s : interactions) {
			System.out.println(s);
		}
	}
}