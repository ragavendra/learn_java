import java.util.Iterator;
import java.util.LinkedList;

public class ImplIter implements Iterable<String>{

	LinkedList<String> link = new LinkedList();

	public ImplIter(){
		link.add("one");
		link.add("two");
		link.add("three");
	}

	class ImplIterat implements Iterator<String>{

		int index = 0;

		public boolean hasNext() {
			return link.size() > index;
		}

		public String next() {
			if(hasNext())
				return link.get(index++);
			else
				throw new UnsupportedOperationException("No 'next' ele ");
		}
	}

	public Iterator<String> iterator() {
		return new ImplIterat();
		// TODO Auto-generated method stub
		// throw new UnsupportedOperationException("Unimplemented method 'iterator'");
	}

	public static void main(String vars[]){
		var impl = new ImplIter();
		
		// can itr as impl is Iterable
		for(String ite: impl) {
			System.out.printf("Item is %s\n", ite);
		}
	}
}

