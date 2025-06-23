import static java.lang.Thread.sleep;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// import jakarta.validation.Valid;
class ExecutorSrvc implements Runnable {

	public void run() {
		try {
			sleep(1000);
			System.out.println("Finished thr run");
		} catch (InterruptedException ex) {
		}
	}

	public static void main(String args[]){
		// Can be used where a collection can be looped in parallel - thred safe
		// CountDownLatch cdl = new CountDownLatch(3);
		ExecutorService es = Executors.newFixedThreadPool(2);
		es.submit(new ExecutorSrvc());
		es.submit(new ExecutorSrvc());
		es.submit(new ExecutorSrvc());
		es.submit(new ExecutorSrvc());
		es.submit(new ExecutorSrvc());
		es.submit(new ExecutorSrvc());

		// close and stop accepting new threads but existing threads will be
		// let to run till compltion
		es.shutdown();
		try {
			// force end threadpool and existing threads in it in the time limit
			es.awaitTermination(1, TimeUnit.DAYS);

		} catch (InterruptedException ex) {
		}
	}
}
