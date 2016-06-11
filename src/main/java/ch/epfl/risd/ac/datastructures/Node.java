package ch.epfl.risd.ac.datastructures;

import java.util.ArrayList;

import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.Transformer;

import ch.epfl.risd.ac.helpers.Helpers;

/**
 * This class models a Node in one generic tree data structure, where each node
 * is having certain number of children.
 * 
 * @param <T>
 *            - the type of data stored in the Node
 */
public class Node<T> {

	/****************************************************************************/
	/* VARIABLES */
	/***************************************************************************/

	/* The list of children of the Node */
	protected ArrayList<Node<T>> children = new ArrayList<Node<T>>();

	/* The parent of the Node */
	protected Node<T> parent;

	/* The data stored in the Node */
	public T data;

	/**
	 * Creating a new Node with a given data and parent.
	 * 
	 * @param value
	 *            - the value of the data to be stored
	 * @param parent
	 *            - the parent Node of this Node
	 */
	public Node(T value, Node<T> parent) {
		this.parent = parent;
		this.data = value;
		if (parent != null)
			parent.addChild(this);
	}

	/**
	 * Creating a new Node with a given data and no parent.
	 * 
	 * @param value
	 *            - the value of the data to be stored
	 */
	public Node(T value) {
		this.parent = null;
		this.data = value;
	}

	/**
	 * Method for adding a child Node to the current Node
	 * 
	 * @param child
	 *            - the child Node to be added
	 * @return true if the adding was successful, false otherwise
	 */
	public boolean addChild(Node<T> child) {
		this.children.add(child);
		return true;
	}

	/**
	 * Method for checking whether the current Node is having children.
	 * 
	 * @return true if the Node is having children, false otherwise
	 */
	public boolean hasChildren() {
		return !this.children.isEmpty();
	}

	/**
	 * Method for attaching the current Node to a parent Node.
	 * 
	 * @param parent
	 *            - the parent Node to which the current Node should be attached
	 * @return true if the attaching was successful, false otherwise
	 */
	public boolean attachToNode(Node<T> parent) {
		if (parent != null) {
			if (parent.addChild(this)) {
				this.parent = parent;
				return true;
			}
		}
		return false;
	}

	/**
	 * Method for re-attaching the current Node to some other parent Node
	 * 
	 * @param newParent
	 *            - the new parent Node of the current Node
	 * @return true if the re-attaching was successful, false otherwise
	 */
	public boolean reattach(Node<T> newParent) {
		if (this.parent != null) {
			this.parent.removeChild(this);
		}
		return this.attachToNode(newParent);
	}

	/**
	 * Method for removing a child Node at a specified position in the list of
	 * children of the current Node
	 * 
	 * @param index
	 *            - the index position of the Node to be removed
	 * @return the removed Node if the index is in the range of possible
	 *         positions, null otherwise
	 */
	public Node<T> removeChild(int index) {
		if (this.children.size() <= index || index < 0)
			return null;
		else {
			Node<T> child = this.children.remove(index);
			child.parent = null;
			return child;
		}
	}

	/**
	 * Method for removing a child Node from the list of children of the current
	 * Node
	 * 
	 * @param child
	 *            - the child Node to be removed
	 * @return true if the removing was successful, false otherwise
	 */
	public boolean removeChild(Node<T> child) {
		if (child == null)
			return false;
		child.parent = null;
		return this.children.remove(child);
	}

	/**
	 * Method for replacing the current Node with a
	 * 
	 * @param newnode
	 * @return
	 */
	public Node<T> replaceNode(Node<T> newnode) {
		Node<T> parent = this.parent;
		newnode.parent = parent;
		if (parent != null)
			parent.children.set(parent.children.indexOf(this), newnode);
		this.parent = null;
		return newnode;
	}

	public Node<T> dFS(Transformer tr) {
		if (this == null)
			return null;
		for (int i = 0; i < this.children.size(); i++) {
			this.children.set(i, this.children.get(i).dFS(tr));
		}
		return Helpers.cast(tr.transform(this));
	}

	public void dFS(Closure cl) {
		if (this == null)
			return;
		for (Node<T> ch : this.children) {
			ch.dFS(cl);
		}
		cl.execute(this);
	}

	public void resetParents() {
		Closure cl = new Closure() {
			public void execute(Object input) {
				Node<T> node = Helpers.cast(input);
				for (Node<T> ch : node.children)
					ch.parent = node;
			}
		};
		if (this != null) {
			this.dFS(cl);
		}
	}

	public <S> S dFS(Transformer tr, S initValue) {
		if (this == null)
			return initValue;
		S result = initValue;
		for (Node<T> child : this.children) {
			result = child.dFS(tr, result);
		}
		return Helpers.cast(tr.transform(new Tuple<S, Node<T>>(result, this)));
	}

	public String printTree() {
		if (this == null)
			return "";
		if (this.children.size() == 0)
			return this.data.toString();
		if (this.children.size() == 1)
			return this.data.toString() + this.children.get(0).printTree();
		if (this.children.size() >= 2) {
			StringBuilder s = new StringBuilder("(" + this.children.get(0).printTree());
			for (int i = 1; i < this.children.size(); i++)
				s.append(this.data.toString() + this.children.get(i).printTree());
			return s.append(")").toString();
		}
		return this.data.toString();
	}

	@Override
	public String toString() {
		return this.printTree();
	}

	public ArrayList<Node<T>> getChildren() {
		return children;
	}

	public Node<T> getParent() {
		return parent;
	}

	public T getData() {
		return data;
	}

}
