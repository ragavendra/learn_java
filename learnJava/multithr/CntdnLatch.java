import static java.lang.Thread.activeCount;
import static java.lang.Thread.sleep;
import java.util.concurrent.CountDownLatch;

// import jakarta.validation.Valid;
class CntdnLatch {
	public static void main(String args[]){
		// Can be used where a collection can be looped in parallel - thred safe
		CountDownLatch cdl = new CountDownLatch(3);

		new Thread(new Runner(cdl)).start();
		new Thread(new Runner(cdl)).start();
		new Thread(new Runner(cdl)).start();

		try {
			cdl.await();
		} catch(Exception e){
			e.printStackTrace();
		}

		// No need to have join as cdl will block till cnt is 0
	}
}

class Runner implements Runnable {

	private CountDownLatch cdl = new CountDownLatch(3);

	Runner(CountDownLatch latch){
		this.cdl = latch;
	}

	public void run() {
		try {
			cdl.countDown();
			System.out.println("Starting thr run " + cdl.getCount());
			// sleep(1000);
		} catch (Exception ex) {
		}
	}


}
