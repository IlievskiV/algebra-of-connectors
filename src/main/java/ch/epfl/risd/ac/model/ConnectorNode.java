package ch.epfl.risd.ac.model;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.risd.ac.datastructures.Node;
import ch.epfl.risd.ac.datastructures.Tuple;

public class ConnectorNode extends Node<Tuple<Boolean, List<String>>>{

	public ConnectorNode(Boolean isTrigger, List<String> data) {
		super(new Tuple<Boolean, List<String>>(isTrigger, 
				(data == null) ? null : new ArrayList<String>(data)));
	}
	
	public ConnectorNode(Boolean isTrigger, List<String> data, 
						Node<Tuple<Boolean, List<String>>> parentNode) {
		super(new Tuple<Boolean, List<String>>(isTrigger, 
			    (data == null) ? null : new ArrayList<String>(data)),
				parentNode);
	}
	
	public void SetType(Boolean type) {
		this.data.setFst(type);
	}
}