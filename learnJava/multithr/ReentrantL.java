import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Sample demo of Reentrant lock, refer to John Purcell's multi threading course
public class ReentrantL {

    public static void main(String[] args) throws InterruptedException {
        var someVar = new ProCons();

        var thr = new Thread(new Runnable() {
            public void run() {
                try {
                    someVar.func1();
                } catch (InterruptedException ex) {
                }
            }

        });

        var thr2 = new Thread(new Runnable() {
            public void run() {
                try {
                    someVar.func2();
                } catch (InterruptedException ex) {
                }
            }

        });

        thr.start();
        thr2.start();

    }
}
/*
A deadlock can happen if there are more than one lock in the system. With say two of them,
in func1 one may try to get the second lock and in func2 one may be trying to get the
first one. There are two ways to overcome this -
a. lock in the same order anywhere or
b. use the below method pattern to get both the locks or wait till both the locks are
available in a while loop and return accordingly.

    private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        while (true) {
            // Acquire locks
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;
            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock) return;
                else if (gotFirstLock) firstLock.unlock();
                else if (gotSecondLock) secondLock.unlock();
            }
            // Locks not acquired
            Thread.sleep(1);
        }
    }

*/

class ProCons {

    public List<Integer> list = new LinkedList<Integer>();

    private Random r = new Random();
    private Lock lock = new ReentrantLock();
    private Condition c = lock.newCondition();

    public void func1() throws InterruptedException {
        System.out.println("Starting Producer");
        lock.lock();
        try {

            System.out.println("Going to wait!");
            c.await();
            System.out.println("Finished wait!");
        }finally {
            lock.unlock();
        }

    }

    public void func2() throws InterruptedException {
        System.out.println("Starting Consumer");

        lock.lock();
        try {
            Thread.sleep(r.nextInt(1000));
            System.out.println("Press Enter!");
            new Scanner(System.in).nextLine();
            c.signal();
        } finally {
			// lock must be unlocked for signal to continue runnin thrd1
            lock.unlock();
        }
    }
}
