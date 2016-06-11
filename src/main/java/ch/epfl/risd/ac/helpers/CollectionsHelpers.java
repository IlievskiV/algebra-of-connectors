package ch.epfl.risd.ac.helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.collections4.Transformer;

import ch.epfl.risd.ac.datastructures.Tuple;

public class CollectionsHelpers {

	    //Try pop from stack
		public static <T> T tryPop(Stack<T> stack) {
			if (stack.isEmpty()) return null;
			else return stack.pop();		 
		}
		
		//Try get from HashMap
		public static <K,V> Tuple<Boolean,V> tryGet(Map<K,V> map, K key) {
			if (map.containsKey(key)) return new Tuple<Boolean, V>(true, map.get(key));
			else return new Tuple<Boolean, V>(false,null);
		}
		
		//Get some element from set
		public static <T> T getFromSet(HashSet<T> set) {
			if (set == null) return null;
			Iterator<T> iter = set.iterator();
			if (iter.hasNext()) return iter.next();
			else return null;
		}
		
		//Copy Set into list
		public static <T> List<T> setToList(Set<T> set) {
			return new ArrayList<T>(set);
//			List<T> res = new ArrayList<T>();
//			Iterator<T> iter = set.iterator();
//			while (iter.hasNext())
//				res.add(iter.next());
//			return res;
		}
		
		//Generate an arrayList with element
		public static <T> List<T> newListWithElem(T elem) {
			List<T> lst = new ArrayList<T>();
			lst.add(elem);
			return lst;
		}
		
		
		//Return {transformer(a,b) | a in first, b in second}
		public static <T> List<T> collect2(List<T> first, List<T> second, Transformer transformer) {
			 List<T> result = new ArrayList<T>();
			 for (T f:first) {
				 for (T s:second) {
					 Tuple<T, T> tuple = new Tuple<T, T>(f, s); 
					 try {
						 T transform = Helpers.cast(transformer.transform(tuple));
						 result.add(transform);
					 }
					 catch (Exception E) {
						 System.out.println("Incorrect Transformer");
						 return null;
					 }			
				 }
			 }
			 return result;
		}
		
		//{a,b,c,d,...n} ->tr(... tr(tr(startValue, a),b),...,n)
		//Transformer requires a Tuple as an input
		public static <T,V> T aggregate(List<V> lst, Transformer transformer, T startValue) {
			for (V el:lst) {
				if (el != null) {
					Tuple<T,V> tuple = new Tuple<T, V>(startValue, el);
					startValue = Helpers.cast(transformer.transform(tuple));
				}
			}
			return startValue;
		}
		
		//{a,b,c,d,...n} ->tr(... tr(tr(startValue, a),b),...,n)
		//Transformer requires a Tuple as an input
		public static <T,V> T aggregate(Set<V> set, Transformer transformer, T startValue) {
			return aggregate(setToList(set), transformer, startValue);
		}
		
		//Clone the object List<List<T>>
		public static <T> List<List<T>> listClone(List<List<T>> lst) {
			if (lst == null) return null;
			List<List<T>> result = new ArrayList<List<T>>();
			for (List<T> l:lst)
				result.add((l == null) ? null : new ArrayList<T>(l));
			return result;
		}
		
		//Converts List<List<T>> into List<T>. Makes a list of all elements
		public static <T> List<T> concat(List<List<T>> input) {
			List<T> result = new ArrayList<T>();
			for (int i=0; i< input.size(); i++) {
				if (input.get(i) != null)
					result.addAll(input.get(i));
			}
			return result;
		}
		
		//remove null values from the list
		public static <T> void removeNulls(List<T> input) {
			if (input == null) return;
			int i=0;
			while (i< input.size()) {
				if (input.get(i) == null) 
					input.remove(i);
				else 
					i++;
			}
		}
}
