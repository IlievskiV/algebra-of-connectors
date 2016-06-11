package ch.epfl.risd.ac.helpers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Transformer;

import ch.epfl.risd.ac.datastructures.Node;

public class PortChecker {

	public static <T> void checkPorts(Node<T> node, Transformer tr, Set<String> ports) {
		check(node.dFS(tr, new HashSet<String>()), ports);
	}

	public static <T> void checkPorts(Set<String> rules, Set<String> ports) {
		check(new HashSet<String>(rules), ports);
	}

	private static void check(Set<String> usedPorts, Set<String> ports) {
		if (ports == null)
			ports = new HashSet<String>();
		usedPorts.remove("true");
		usedPorts.remove("false");
		usedPorts.remove("+");
		usedPorts.remove("*");
		usedPorts.remove("!");
		if (!CollectionUtils.isSubCollection(usedPorts, ports)) {
			System.err.println("Not all ports were in list");
			List<String> tmp = Helpers.cast(CollectionUtils.union(ports, usedPorts));
			ports.clear();
			ports.addAll(tmp);
		}
	}

}
