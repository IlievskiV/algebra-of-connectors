package ch.epfl.risd.ac.helpers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.epfl.risd.ac.datastructures.Node;

public class Helpers {

	/// return 0 - if they are equal, -1 - snd is more than fst, 1 fst is more
	/// than snd, -2 if they are noncomparable
	public static int compareSortedLists(List<String> fst, List<String> snd) {
		if (fst == null || snd == null)
			return -2;
		int i = 0;
		int j = 0;
		int comp;
		int res = 0;
		while (i < fst.size() && j < snd.size()) {
			comp = fst.get(i).compareTo(snd.get(j));
			if (comp == 0) {
				i++;
				j++;
			} else if (comp < 0) {
				if (res == -1)
					return -2;
				else {
					res = 1;
					i++;
				}
			} else {
				{
					if (res == 1)
						return -2;
					else {
						res = -1;
						j++;
					}
				}
			}
		}
		if (i == fst.size() && j == snd.size())
			return res;
		if (i == fst.size() && res != 1)
			return -1;
		if (j == snd.size() && res != -1)
			return 1;
		return -2;
	}

	/// return 0 - if they are equal, -1 - snd is more than fst, 1 fst is more
	/// than snd, -2 if they are noncomparable
	public static int compareLists(List<String> fst, List<String> snd) {
		if (fst == null || snd == null)
			return -2;
		Collections.sort(fst);
		Collections.sort(snd);
		return compareSortedLists(fst, snd);
	}

	// Remove elements from List<List>, which are greater or equal than other
	// elements
	public static void simplifyClausesCausalRule(List<List<String>> lst) {
		if (lst == null)
			return;
		CollectionsHelpers.removeNulls(lst);
		int i = 0;
		while (i < lst.size()) {
			List<String> fst = lst.get(i);
			int j = i + 1;
			Boolean tobreak = false;
			while (j < lst.size() && !tobreak) {
				List<String> snd = lst.get(j);
				int comp = compareLists(fst, snd);
				if (comp == 0 || comp == -1)
					lst.remove(snd);
				else if (comp == 1)
					tobreak = true;
				else
					j++;
			}
			if (tobreak)
				lst.remove(i);
			else
				i++;
		}
	}

	// remove duplicate elements from list and sort it
	public static void removeDuplicates(List<String> lst) {
		if (lst == null)
			return;
		CollectionsHelpers.removeNulls(lst);
		Collections.sort(lst);
		int k = 0;
		while (k < lst.size() - 1) {
			if (lst.get(k).equals(lst.get(k + 1)))
				lst.remove(k + 1);
			else
				k++;
		}
	}

	// Print all elements of list
	public static String listToString(List<String> lst) {
		if (lst == null)
			return "";
		StringBuilder str = new StringBuilder();
		for (String l : lst)
			str.append(l);
		return str.toString();
	}

	// Print all elements of list separated by separator
	public static String listToString(List<String> lst, String separator) {
		if (lst == null || lst.size() == 0)
			return "";
		StringBuilder str = new StringBuilder();
		str.append(lst.get(0));
		for (int i = 1; i < lst.size(); i++)
			str.append(separator + lst.get(i));
		return str.toString();
	}

	public static String otherOp(String op) {
		if (op.equals("*"))
			return "+";
		else if (op.equals("+"))
			return "*";
		else
			return "";
	}

	public static Node<String> copySubtree(Node<String> node) {
		if (node == null)
			return null;
		Node<String> newNode = new Node<String>(node.getData());
		for (Node<String> child : node.getChildren())
			copySubtree(child).attachToNode(newNode);
		return newNode;
	}

	public static Node<List<String>> copySubtreeListNode(Node<List<String>> node) {
		if (node == null)
			return null;
		Node<List<String>> newNode = (node.getData() == null) ? new Node<List<String>>(new ArrayList<String>())
				: new Node<List<String>>(new ArrayList<String>(node.getData()));
		for (Node<List<String>> child : node.getChildren())
			Helpers.copySubtreeListNode(child).attachToNode(newNode);
		return newNode;
	}

	// Function to avoid writing suppress warning everywhere
	@SuppressWarnings("unchecked")
	public static <T> T cast(Object o) {
		try {
			return (T) o;
		} catch (Exception e) {
			System.err.println("Invalid cast" + e.getMessage());
			return null;
		}
	}

	// cartesian product of two lists
	public static List<List<String>> multiply(List<List<String>> fst, List<List<String>> snd) {
		if (fst == null || snd == null)
			return null;
		if (fst.size() == 0)
			return snd;
		if (snd.size() == 0) {
			System.err.println("Snd list is empty.");
			return fst;
		}
		List<List<String>> res = new ArrayList<List<String>>();
		for (int i = 0; i < fst.size(); i++) {
			for (int j = 0; j < snd.size(); j++) {
				List<String> arr = new ArrayList<String>(fst.get(i));
				arr.addAll(snd.get(j));
				Helpers.removeDuplicates(arr);
				res.add(arr);
			}
		}
		return res;
	}
}
