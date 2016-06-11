package ch.epfl.risd.ac.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import ch.epfl.risd.ac.datastructures.Node;
import ch.epfl.risd.ac.datastructures.Tuple;
import ch.epfl.risd.ac.helpers.CollectionsHelpers;
import ch.epfl.risd.ac.helpers.Helpers;
import ch.epfl.risd.ac.helpers.PortChecker;

/**
 * This class models the causal tree of possible interactions, if we follow the
 * rules of the algebra of connectors. Using this tree we can generate all
 * possible interactions.
 */
public class CausalTree {

	public Node<List<String>> ctRoot;

	public Set<String> ports;

	private void checkPorts() {
		Transformer tr = new Transformer() {
			public Object transform(Object arg0) {
				Tuple<Set<String>, Node<List<String>>> input = Helpers.cast(arg0);
				if (input.snd() != null)
					input.fst().addAll(input.snd().getData());
				return input.fst();
			}
		};
		PortChecker.checkPorts(ctRoot, tr, ports);
	}

	public CausalTree() {
		this.ports = new HashSet<String>();
		ctRoot = new Node<List<String>>(new ArrayList<String>());
	}

	public CausalTree(Connector cn) {
		if (cn != null) {
			this.ports = cn.ports;
			ctRoot = generateTree(cn);
			checkPorts();
		}

		System.err.println("Causal Tree  ");
		System.err.println(this.print());

		cleanTree();

		System.err.println("Causal Tree Cleaned  ");
		System.err.println(this.print());

	}

	/**************************************************************************************
	 * Print
	 ***************************************************************************************/

	private String printTree(Node<List<String>> node) {

		StringBuilder result = new StringBuilder();

		if (node != null) {
			result.append(Helpers.listToString(node.getData()));

			if (node.getChildren().size() != 0) {

				result.append("{" + printTree(node.getChildren().get(0)));

				for (int i = 1; i < node.getChildren().size(); i++)
					result.append(";" + printTree(node.getChildren().get(i)));
				result.append("}");
			}

		}

		return result.toString();
	}

	public String print() {
		if (this.ctRoot == null)
			return "";
		return printTree(ctRoot);
	}

	private Node<List<String>> generateTreeSum(Node<List<String>> fst, Node<List<String>> snd) {
		if (snd == null)
			return fst;
		if (fst == null)
			return snd;

		fst.getData().addAll(snd.getData());
		fst.getChildren().addAll(snd.getChildren());
		return fst;
	}

	private List<Node<List<String>>> generateTreeSynch(List<List<Node<List<String>>>> subtreesSynch) {
		if (subtreesSynch == null || subtreesSynch.size() == 0)
			return new ArrayList<Node<List<String>>>();
		if (subtreesSynch.size() == 1)
			return subtreesSynch.get(0);
		List<Node<List<String>>> first = subtreesSynch.get(0);
		List<Node<List<String>>> second = subtreesSynch.get(1);
		subtreesSynch.remove(0);
		subtreesSynch.remove(1);
		List<Node<List<String>>> result = new ArrayList<Node<List<String>>>();
		for (int i = 0; i < first.size(); i++) {
			Node<List<String>> fstNode = first.get(i);
			for (int j = 0; j < second.size(); j++) {
				Node<List<String>> sndNode = second.get(j);
				result.add(generateTreeSum(fstNode, sndNode));
			}
		}
		return generateTreeSynch(subtreesSynch);
	}

	private Node<List<String>> newNode(List<String> lst) {
		ArrayList<String> l = (lst == null) ? null : new ArrayList<String>(lst);
		return new Node<List<String>>(l);
	}

	private List<Node<List<String>>> generateTreeInner(Node<Tuple<Boolean, List<String>>> nodeC) {
		if (!nodeC.hasChildren())
			return CollectionsHelpers.newListWithElem(newNode(nodeC.getData().y));

		List<List<Node<List<String>>>> subtreesSynch = new ArrayList<List<Node<List<String>>>>();
		List<Node<List<String>>> subtreesTrigg = new ArrayList<Node<List<String>>>();
		for (int i = 0; i < nodeC.getChildren().size(); i++) {
			if (nodeC.getChildren().get(i).getData().x)
				subtreesTrigg.addAll(generateTreeInner(nodeC.getChildren().get(i)));
			else
				subtreesSynch.add(generateTreeInner(nodeC.getChildren().get(i)));
		}

		if (subtreesSynch.size() == 0)
			return subtreesTrigg;
		if (subtreesTrigg.size() == 0)
			return generateTreeSynch(subtreesSynch);
		List<Node<List<String>>> subtreesSynchConcat = CollectionsHelpers.concat(subtreesSynch);
		for (int i = 0; i < subtreesSynchConcat.size(); i++) {
			Node<List<String>> synch = subtreesSynchConcat.get(i);
			for (int j = 0; j < subtreesTrigg.size(); j++)
				Helpers.copySubtreeListNode(synch).attachToNode(subtreesTrigg.get(j));
		}
		return subtreesTrigg;
	}

	/**************************************************************************************
	 * Generation from Connector
	 ***************************************************************************************/
	private Node<List<String>> generateTree(Connector cn) {
		if (cn == null || cn.root == null)
			return null;
		Node<List<String>> root = new Node<List<String>>(new ArrayList<String>());
		List<Node<List<String>>> trees = generateTreeInner(cn.root);
		for (int i = 0; i < trees.size(); i++)
			trees.get(i).attachToNode(root);
		return root;
	}

	/**************************************************************************************
	 * Tree cleaning
	 ***************************************************************************************/

	public void cleanTree() {
		Transformer tr = new Transformer() {
			public Object transform(Object arg0) {
				Node<List<String>> node = Helpers.cast(arg0);
				Node<List<String>> parent = node.getParent();
				while (parent != null) {
					node.data = Helpers.cast(CollectionUtils.subtract(node.getData(), parent.getData()));
					parent = parent.getParent();
				}
				return node;
			}
		};
		if (this.ctRoot != null)
			ctRoot.dFS(tr);
	}

	/**************************************************************************************
	 * Generators
	 ***************************************************************************************/

	public Connector toConnector() {
		return new Connector(this);
	}

	public List<String> getInteractions(Node<List<String>> node) {

		/* Initialize the empty list */
		List<String> result = new LinkedList<String>();

		/* The node is not having children */
		if (node.getChildren().size() == 0) {
			result.add(Helpers.listToString(node.data));
		} else {
			/* Take the current interaction */
			String current = Helpers.listToString(node.data);

			/* Add the current one */
			result.add(current);

			/* Results from all children */
			List<List<String>> allChildrenResult = new LinkedList<List<String>>();

			/* Iterate over them */
			for (int i = 0; i < node.getChildren().size(); i++) {
				allChildrenResult.add(getInteractions(node.getChildren().get(i)));
			}

			/* Combine the lists */
			List<String> left = allChildrenResult.get(0);

			/* Iterate over the results */
			for (int i = 1; i < allChildrenResult.size(); i++) {
				/* Create temporary list */
				List<String> temp = new LinkedList<String>();

				/* Get the right list */
				List<String> right = allChildrenResult.get(i);

				/* Iterate over the left and right list */
				for (String l : left) {
					for (String r : right) {
						/* Merge and add them */
						temp.add(l + r);
					}
				}

				left = temp;
			}

			/* Iterate over the left */
			for (String l : left) {
				result.add(current + l);
			}

		}

		return result;
	}

	public List<String> getInteractions() {
		return getInteractions(ctRoot.getChildren().get(0));
	}

}