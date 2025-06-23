import static java.lang.Thread.sleep;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Sample low level lock
public class Lockd {

    private static final Object lock = new Object();
    private static final Lock lock_ = new ReentrantLock();

    public static void main(String[] args) {
        ExecutorService threadpool = Executors.newCachedThreadPool();
/*        threadpool.submit(() -> main_(1));
        threadpool.submit(() -> main_(2));
        threadpool.submit(() -> main_(3));*/
        threadpool.submit(() -> synchro(1));
        // threadpool.submit(() -> synchro(2));
        // threadpool.submit(() -> synchro(3));

		// w/o this the main pgm will not finish
		threadpool.shutdown();
    }

// wait until the lock is available
    public static void synchro(int no) {

        for (int i = 0; i < 10; i++) {
            synchronized (lock) {
                try {
                    sleep(1000);
                } catch (InterruptedException ex) {
                    System.out.println("ex " + ex);
                }
                System.out.println("no " + no + " ite" + i);
            }

        }
    }

// if lock not available at check, it will not run
    public static void main(int no) {

        for (int i = 0; i < 10; i++) {

            // if (lock_.tryLock(1, TimeUnit.SECONDS)) {
            if (lock_.tryLock()) {
                try {
                    // synchronized (lock) {
                    sleep(1000);
                    System.out.println("no " + no + " ite" + i);
                    // }

                } catch (InterruptedException ex) {
                    System.out.println("ex " + ex);
                }

            }
        }
    }
}
