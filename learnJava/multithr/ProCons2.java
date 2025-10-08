import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

// Pro Cons to populate and empty the list in a low level thread safe manner
public class ProCons2 {

    public static void main(String[] args) throws InterruptedException {
        var someVar = new ProCons();
		System.out.println("This is ProCons2 class.");

        var thr = new Thread(new Runnable() {
            public void run() {
                try {
                    someVar.produce();
                } catch (InterruptedException ex) {
                }
            }

        });

        var thr2 = new Thread(new Runnable() {
            public void run() {
                try {
                    someVar.consumer();
                } catch (InterruptedException ex) {
                }
            }
        });

        thr.start();
        thr2.start();

        // Thread.sleep(30000);

        // System.exit(0);
    }
}

class ProCons {

    public List<Integer> list = new LinkedList<Integer>();

    private Object lock = new Object();

    private Random r = new Random();

    public void produce() throws InterruptedException {
        System.out.println("Starting Producer");

        while (true) {
            synchronized (lock) {
/* 
				Map<Integer, String> map = Map.of(2, "", 3, "");
				HashMap<Integer, String> hash = new HashMap<Integer, String>();
				TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
				LinkedHashMap<Integer, String> linkedHashMap = new LinkedHashMap<Integer, String>();
				treeMap.put(3, "");
				
				Collections.sort(list);

				for(var ent:map.entrySet()){
					System.out.println(ent.getKey());
					System.out.println(ent.getValue());
				}

*/
                while (list.size() == 10) {
                    lock.wait();
                }

				int no = r.nextInt(); 
                list.add(no);
                System.out.printf("Added item %s\n", no);
                lock.notify();
            }
        }
    }

    public void consumer() throws InterruptedException {
        System.out.println("Starting Consumer");
        while (true) {
            synchronized (lock) {
                while (list.size() == 0) {
                    lock.wait();
                }
                System.out.printf("Removed item %s\n", list.remove(0));
				// String.format("%d is %3.1f", 3, 33.242);
				// System.out.printf("%d is %3.1f\n", 3, 33.242);
				// String.format("No is {list.remove(3)}");
                lock.notify();
            }

            Thread.sleep(r.nextInt(1000));
        }
    }
}
