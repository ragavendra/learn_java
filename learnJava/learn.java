package learnJava;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.SortedSet;
import java.util.Spliterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.DoublePredicate;
import java.util.function.Function;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.swing.text.html.HTMLDocument;

// iw = Greatest sum of sub arrays
// Online Java Compiler
// Use this editor to write, compile and run your Java code online

class Main {
	public static void main(String[] args) {
		var per1i = new Person("Fir1", "Las1");
		var cmp = new Compare();
		List<Person> perLis = new ArrayList(){
			public String toString(){
				var res = "";
				var len = this.size();
				while(len-- > 0){
					// res = res + it.toString();
					var it = this.iterator().next().toString() + "; ";
					System.out.println("Item is " + it);
					res = res + it;
				}
				return res;
			}
		};
		var per1 = new Person("Fir1", "Las1");
		perLis.add(per1);

		var per = new Person("Firs", "Las");
		perLis.add(per);
		perLis.add(new Person("Fir2", "Las2"));

		perLis.sort(cmp);

		System.out.println("Sorted list is " + perLis.toString());

		for(var it: perLis){
			System.out.println("Ite is " + it);
		}

		// int[] input = {24, 33, 16, 35, 15, 21, 33, 55, 77, 10, 75};

		int[] input = {-2,1,-3,4,-1,2,1,-5,4};
		int arr[] = {2, 3};

		for(int ele:arr){
			System.out.println("Ele is " + ele);
		}

		// int[] ind = {0, 3, 4, 6, 7, 10};
		int[] ind = {0, 3, 3, 6, 6, 8};
		// -1, 1, -1, -4

		// ArraySum(input, ind);
		System.out.println("Result is " + ArraySum(input, ind));

	}

	public static int ArraySum(int[] input, int[] ind) {

		// int[] resSum = new int[ind.size()/2];
		// ind = {0, 6, 7, 15, 16, 26};
		int sum = 0;
		int sumLoop = 0;
		int st = 0;
		int end = 0;
		// int times = ind.length/2;

		// loops no. of sub array times
		for(int i=0; i< ind.length; i = i+2) {
			st = ind[i];
			end = ind[i+1];
			System.out.println("St " + st);
			System.out.println("En " + end);

			// loop each sub array
			for(int y=st; y<end; ++y){
				//for(int z = input[y]; z < input[end]; z++) {
				System.out.println("input[y] " + input[y]);

				sum = sum+input[y];
				//}
			}

			System.out.println("Res is " + sum);

			if(sum > sumLoop)
				sumLoop = sum;

			sum = 0;

		}


		return sumLoop;
	}
}

/* 
class Compare<Person> implements Comparator<Person> {

	public int compare(Person arg0, Person arg1) {
		// getting undef sym err
		// return arg0.getFirstName().compareTo(arg1.getFirstName());
		arg0 = new Person("fir", "las");
		arg0.getFirstName();
		return 1;
	}
}
class Person{
	private String firstName;

	private String lastName;

	public Person(String fir, String las){
		firstName = fir;
		lastName = las;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String toString(){
		return firstName + " " + lastName;
	}
}
*/

public class learn {

	public void CopyStream() {
		List<String> strings = List.of("one", "two", "three", "four");
		Map<String, String>  map = Map.of("key", "val", "2", "val2");
		Function<String, Integer> toLength = String::length;
		List<Integer> ints = strings.stream()
			.map(toLength)
			.collect(Collectors.toList());

		var ints_ = strings.stream()
			.mapToInt(String::length)
			.summaryStatistics();

		System.out.println("List is " + ints);

	}

	public void CallableFuture() {
		ExecutorService es = Executors.newCachedThreadPool();
		Future<?> f = es.submit(new Callable<Void>() {
			public Void call() {
				return null;
			}
		});

		Future<String> fS = es.submit(new Callable<String>() {
			public String call() {
				return "";
			}
		});

		try {
			f.get();
			System.err.println("Future Res is " + fS.get());
		} catch (InterruptedException ex) {
		} catch (ExecutionException ex) {
		}
	}

	public void StreamsMapFilterReduce() {
		String fil = "/tmp/so", str = "rama";
		try(var strm = Files.lines(Path.of(fil))) {
			long count = strm.filter(line -> line.contains(str)).count();

			// var c = strm.takeWhile(bo -> bo.contains('bo'));
			System.out.println("Found " + count + " of " + str);
		} catch (IOException ex) {
			System.err.println("Exception is " + ex.toString());
		}

		IntStream.range(0, 7)
			// .forEach(ic -> System.err.println("Ele is " + ic));
			.takeWhile(no -> no > 2)
			.forEach(System.out::println);

		// IntStream.iterate(0, 8);
		List<String> list = List.of("one", "two", "three");
		list
			.stream()
			.filter(pr -> pr.contains("pr"))
			.forEach(cr -> System.out.println("" + cr));

		list
			.stream()
			// .map(ele -> ele.toUpperCase())
			.filter(pr -> pr.length() == 3)
			.map(String::toUpperCase)
			// .forEach(ele -> System.out.println(ele));
			.forEach(System.out::println);
	}

	public void Semaphore(Semaphore s) {
		try {
			s.acquire();
			s.release();
			s.wait();
			s.acquire();
			s.release();

		} catch (InterruptedException ex) {
		}
	}

	public void Semaphore_(Semaphore s) {
		// Semaphore s = new Semaphore(1, true);
		try {
			s.acquire();

			// wake up waiting thrds
			s.notify();

			s.release();

		} catch (InterruptedException ex) {
		}
	}

	public void useThr() {
		// Can be used for thread safe operations without synchronized
		// ArrayBlockingQueue abq = new ArrayBlockingQueue(10);
		SortedSet<Integer> sortedSet = null;
		// sortedSet.add(1);

		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);

		Collections.sort(list, new Comparator<Integer>(){
			public int compare(Integer n1, Integer n2){
				return -1;
			}
		});

		/*
		   Thread1 thread2 = new Thread1();
		   thread2.start();
		   */
		/*
		   Thread thr1 = new Thread(new Runnable1());
		   thr1.start();

		// thr1.interrupt();

		Thread thr2 = new Thread(new Runnable1());
		thr2.start();

		Thread thr3 = new Thread(new Runnable() {
		public void run() {
		try {
		sleep(100);
		} catch (InterruptedException ex) {
		}
		}
		});
		thr2.start();

*/    }

		public static int[] arrayOfMultiples(int num, int length) {

			int[] arr = new int[length];
			arr[0] = 7;
			// int arrr[] = { 3, 34, 5};

			for (int i = 0; i < length; i++) {
				// i++;
				arr[i] = num * (i + 1);
			}

			return arr;
		}

		public static int sum(int[] arr) {

			if (arr.length == 0) {
				return 0;
			} else if (arr.length == 1) {
				return arr[0];
			}

			int no = 0;
			int i = 0;

			while (i > 0) {
				no = no + arr[i++];
				sum(arr);
			}

			return no;
		}

		public static int sum_(int[] arr) {

			if (arr.length == 0) {
				return 0;
			} else if (arr.length == 1) {
				return arr[0];
			}

			int[] no = new int[arr.length - 1];
			no[arr.length - 2] = arr[arr.length - 1] + arr[arr.length - 2];
			return sum_(no);

			// return no[0];
		}

		public static void StreamSample() {
			String[] arr = new String[]{"a", "b", "c"};
			Stream<String> stream = Arrays.stream(arr);
			// stream = Stream.of("a", "b", "c");

			HashMap<String, String> rec;
			// SortedMap<String, String> rec;

			ArrayList<String> al = new ArrayList<String>();
			al.add("one");
			al.add("two");
			stream = al.stream();

			al.parallelStream().forEach(ele -> System.out.println(ele));

			long len = al.stream().distinct().count();
			al.stream().distinct();

			boolean isExist = al.stream().anyMatch(element -> element == "a");

			Stream<String> stream_ = al.stream().filter(element -> element == "d");

			// String str = () -> System.out.println("");
			new Thread(() -> {
				System.out.println("New thread created");
			}).start();

			var queue = new ArrayBlockingQueue(3);
			queue.add("len");
			queue.add("two");
			queue.add("three");
			queue.remove();
			try {
				// wait until queue is empty
				queue.put("len");
			} catch (InterruptedException ex) {
			}
			try {
				// wait until queue has atleast one item
				queue.take();
			} catch (InterruptedException ex) {
			}

			Iterator<String> it = queue.iterator();
			it.next(); // gives len?
			it.next(); // gives two?

			LinkedList list = new LinkedList<String>();
			list.add("e");
			list.add("w");
			list.add("o");
			ListIterator<String> listIterator = list.listIterator();
			listIterator.next();

			// can go back as well
			listIterator.previous();
		}

		public void Comparator(){

			ArrayList<String> al = new ArrayList<String>();

			// sort in natural order
			Collections.sort(al);

			// Collections.sort(al, new SampleClass());
			// anonymous class
			Collections.sort(al, new Comparator<String>() {
				public int compare(String t, String t1) {
					return -t.compareTo(t1); // reverse alpha
											 // throw new UnsupportedOperationException("Not supported yet.");
				}

			});
		}

		public class ImplIter implements Iterable<String>{

			LinkedList<String> link = new LinkedList();

			class ImplIterat implements Iterator<String>{

				int index = 0;

				public boolean hasNext() {
					return link.size() > index;
				}

				public String next() {
					if(hasNext())
						return link.get(index);
					else
						throw new UnsupportedOperationException("No 'next' ele ");
				}
			}

			public Iterator<String> iterator() {
				return new ImplIterat();
				// TODO Auto-generated method stub
				// throw new UnsupportedOperationException("Unimplemented method 'iterator'");
			}
		}

		public class SampleClass implements Comparator<String> {

			//@Override
			public int compare(String t, String t1) {
				// throw new UnsupportedOperationException("Not supported yet.");
				// for t < t1
				// return 1;
				// else ie for equal
				// return 0;
				if (t.length() < t.length())
					return -1;
				else
					return 1;

				// return 0;
			}

		}

		public static void var(String par) {
			System.out.println("" + par);
			String[] arr = new String[]{"a", "b", "c", "d", "e"};
			Stream<String> streamOfArrayFull = Arrays.stream(arr);
			Arrays.stream(arr, 1, 3);

			Sum s = (int x, int y) -> x + y;

			System.out.println("Sum is " + s.calc(3, 9));

			Cons cs = (Double in) -> System.out.println("Input processed is " + in);
			cs.accept(2.3);

			Pre pr = (double in) -> in > 3;
			System.out.println("Pred res is " + pr.test(2.2));

			Funct func = (Integer w) -> w + 1;
			System.out.println("Func res is " + func.apply(2));

			Pro pro = () -> 2;
			System.out.println("Pro res is " + pro.get());

			// FileOutputStream
			// ObjectOutputStream
		}

		interface Cons extends Consumer<Double> {

			@Override
			public void accept(Double in);
		}

		// interface Pre extends Predicate<Double> {
		interface Pre extends DoublePredicate {

			@Override
			public boolean test(double in);
		}

		interface Funct extends Function<Integer, Integer> {

			@Override
			public Integer apply(Integer d);
		}

		interface Pro extends Supplier<Integer> {

			@Override
			Integer get();
		}

		// functional interface
		public interface Sum {

			public int calc(int x, int y);
			// public String calc(int y);
		}
		}

class Spliterator_ implements java.util.Spliterator<String>{

	@Override
	public boolean tryAdvance(Consumer<? super String> cnsmr) {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public Spliterator<String> trySplit() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public long estimateSize() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	public int characteristics() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

}

class ProCons2 {

	public List<Integer> list = new LinkedList<Integer>();

	private Object lock = new Object();

	private Random r = new Random(100);

	public synchronized void produce() throws InterruptedException {
		System.out.println("Starting Producer");

		do {
			synchronized (lock) {
				list.add(r.nextInt());

				do {
					if (list.size() == 10) {
						wait();
					}
					Thread.sleep(100);
				} while (true);
			}
		} while (true);
	}

	public synchronized void consumer() throws InterruptedException {
		System.out.println("Starting Consumer");
		do {
			synchronized (lock) {
				do {
					// list.remove();
					// System.out.println("sdfds sdfd");
					if (list.size() == 0) {
						notify();
					}
				} while (true);
			}
		} while (true);
	}
}

class ProCons {

	public synchronized void produce() throws InterruptedException {
		synchronized (this) {
			System.out.println("Starting Producer");

			Thread.sleep(1000);

			wait();
			System.out.println("Resuming");
		}
	}

	public synchronized void consumer() throws InterruptedException {
		System.out.println("Starting Consumer");
		Thread.sleep(1000);
		synchronized (this) {
			Scanner s = new Scanner(System.in);
			s.nextLine();
			notify();

			Thread.sleep(1000);

		}
	}
}

/*
 * a-->a-->a-->b-->d-->e-->e-->h
 * output: a-->3-->b-->1-->d-->1-->e-->2-->h-->1
 * Number of consecutive chars <= 9
 class ListNodeChar
 {
 char val;
 ListNodeChar next;
 }
 public class ConsNos {

// ListNodeChar listNodeChar;

public ListNodeChar Result(ListNodeChar listNodeChar) {

var listNodeChar_ = listNodeChar_;
listNodeChar_.next = null;

ListNodeChar prev;
char a = listNodeChar_.val;
int count = 0;

while(listNodeChar.next != null) {

if(listNodeChar.val != a)
var d = new ListNodeChar();
d.val = count;
listNodeChar_.next = d;

d = new ListNodeChar();
d.val = listNodeChar.val;
listNodeChar_.next = d;
count = 0;

}
count++;

listNodeChar = listNodeChar.next;

}

return ListNodeChar_;
 }
 public static void Main() {

 ConsNos res = new ConsNos();
 ListNodeChar lis = new ListNodeChar();
 var re = res.result(lis);

 while(re.next != null)
 {
 System.out.println("Element or count is " + re.val);
 }

 }
 */


