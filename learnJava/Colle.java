import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

public class Colle {
	public void Maps(){
		var hashMap = new HashMap<Integer, String>();
		hashMap.put(1, "One");

		var treeMap = new TreeMap<Integer, String>();
		treeMap.put(2, "Two");

		var linkMap = new LinkedHashMap();
		linkMap.put(3, "Three");
		
		// for(<Integer, String> (key, val): treeMap.entrySet()){
		for(Map.Entry<Integer, String> keyVal: treeMap.entrySet()){
			System.out.printf("keyVal is %s and %s", keyVal.getKey(), keyVal.getValue());
		}
	}

	public static void main (String[] args) {
		var set = new HashSet();
		set.add("one");
		set.add("two");
		set.add("three");

		var treeSet = new TreeSet();
		treeSet.add("three");
		treeSet.add("four");
		treeSet.add("five");

		var linkSet = new LinkedHashSet();

		set.retainAll(treeSet);
		System.out.println(set);
	}

}
