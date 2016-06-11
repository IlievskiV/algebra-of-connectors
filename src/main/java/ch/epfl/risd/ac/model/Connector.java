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
import ch.epfl.risd.ac.grammar.ConnectorLexer;
import ch.epfl.risd.ac.grammar.ConnectorParser;
import ch.epfl.risd.ac.helpers.Helpers;
import ch.epfl.risd.ac.helpers.PortChecker;

/**
 * This class models one connector in the sense of the Algebra of Connectors,
 * i.e. one connector is set of communicating ports belonging to different
 * components that may be involved in some interactions. Depending on the types
 * of the ports (synchrons or triggers), the connector can produce different
 * forms of interaction. For this reason, one connector is represented as a tree
 * structure
 */

public class Connector {

	/****************************************************************************/
	/* VARIABLES */
	/***************************************************************************/

	/* The root of the connector, i.e. the initial form of the connector */
	protected ConnectorNode root;

	/* The set of ports included in the connector */
	protected Set<String> ports;

	/****************************************************************************/
	/* PRIVATE(UTILITY) METHODS */
	/****************************************************************************/

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

	/**
	 * Helper method for getting a String representation of the Connector
	 * 
	 * @param node
	 *            - the root of the Connector
	 * @return the String representation of the Connector
	 */
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

	/****************************************************************************/
	/* PUBLIC METHODS */
	/***************************************************************************/

	/**
	 * Creating an empty Connector
	 */
	public Connector() {
		ports = new HashSet<String>();
		root = new ConnectorNode(false, null);
	}

	/**
	 * Creating a new Connector with a set of ports included in the Connector
	 * and the root of the Connector.
	 * 
	 * @param ports
	 *            - the set of ports included in the Connector
	 * @param root
	 *            - the root of the Connector
	 */
	public Connector(Set<String> ports, ConnectorNode root) {
		if (ports != null) {
			this.ports = ports;
		} else {
			this.ports = new HashSet<String>();
		}

		this.root = root;
		checkPorts();

		System.out.println("The following connector was created: " + print());
	}

	/**
	 * 
	 * Creating a new Connector given the Causal Tree of interactions
	 * 
	 * @param causalTree
	 *            - the Causal Tree of interactions
	 */
	public Connector(CausalTree causalTree) {
		if (causalTree != null) {
			this.ports = causalTree.ports;
			root = fromCausalTree(causalTree);
			checkPorts();
		}

		System.out.println("The following connector was created: " + print());
	}

	public String print() {
		if (this.root == null)
			return "";

		return printTree(root);
	}

	/**
	 * Method for creating the root of the Connector, i.e. a Connector Node,
	 * when given a Causal Tree of interactions
	 * 
	 * @param causalTree
	 *            - the Causal Tree of Interactions
	 * @return
	 */
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

	/**
	 * Method for creating a Connector Node given a String
	 * 
	 * @param input
	 *            - the given String
	 * @return the created Connector Node
	 */
	public static ConnectorNode FromString(String input) {
		ConnectorLexer lexer = new ConnectorLexer(new ANTLRInputStream(input));
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		ConnectorParser p = new ConnectorParser(tokens);
		p.setBuildParseTree(true);
		return p.top().c;
	}

	/**
	 * Method for creating Causal Tree from the current Connector
	 * 
	 * @return the created Causal Tree
	 */
	public CausalTree toCausalTree() {
		return new CausalTree(this);
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