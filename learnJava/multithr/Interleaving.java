// package multithr;
import java.util.Scanner;

// R and Ws are atomic for all ref and prim var exc long and double
public class Interleaving{

    private static int count;

    // avoid interleaving - mutex or or monitor lock or mutual exclusion to run one thr at a time
    public static  void increment() {
		count++;
    }

	public static void main(String[] str) throws InterruptedException{

		// Iterleaving inte = new Iterleaving();
		Thread thread1 = new Thread(new Runnable(){
			public void run(){
				// atomic except for long and double
				double no = 1E4; // 1E4 and above having sync issues
				do {
					increment();
				} while (no-- > 0);
			}
		});

		thread1.start();
		// java.lang.thread.sleep(22);

		Thread thread2 = new Thread(new Runnable(){
			public void run(){
				double no = 1E4;
				do {
					increment();
				} while (no-- > 0);
			}
		});
		thread2.start();

		thread1.join();
		thread2.join();

		// Scanner sc = new Scanner(System.in);
		// sc.nextLine();

		System.out.printf("Count is %d and name is %s", count, thread1.getPriority());

		/* interleaving
		// w/o synchronized the resuld may must likely not be 100
		Interleaving thread3 = new Thread2();
		thread3.start();

		Interleaving thread4 = new Thread2();
		thread4.start();

*/
	}
}

class Thread2 extends Thread {

	private int count;

	public void run() {
		do {
			increment();

			if(Thread.currentThread().isInterrupted())
				break;

		} while (count < 100);
	}

	// avoid interleaving - mutex or or monitor lock or mutual exclusion to run one thr at a time
	public synchronized void increment() {
		count++;
	}
}


