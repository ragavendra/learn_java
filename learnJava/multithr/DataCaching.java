// package multithr;

import java.util.Scanner;

public class DataCaching extends Thread {

	// prevent thr caching var data using volatile
	private volatile boolean running = true;

	public void run() {
		do {

			try {
				sleep(100);
				System.out.println("Thr running");
			} catch (InterruptedException ex) {
			}
		} while (running);
	}

	public void shutdown() {
		running = false;
	}

	public static void main(String[] str) throws InterruptedException{
		var thr1 = new DataCaching();
		thr1.start();

		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();

		thr1.shutdown();
	}
}
