import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.Random;

class SortTask extends RecursiveAction {
	final long[] array; final int lo, hi;
	SortTask(long[] array, int lo, int hi) {
		this.array = array; this.lo = lo; this.hi = hi;
	}
	SortTask(long[] array) { this(array, 0, array.length); }
	protected void compute() {
		if (hi - lo < THRESHOLD)
			sortSequentially(lo, hi);
		else {
			int mid = (lo + hi) >>> 1;
			invokeAll(new SortTask(array, lo, mid),
					new SortTask(array, mid, hi));
			merge(lo, mid, hi);
		}
	}
	// implementation details follow:
	static final int THRESHOLD = 1000;
	void sortSequentially(int lo, int hi) {
		Arrays.sort(array, lo, hi);
	}
	void merge(int lo, int mid, int hi) {
		long[] buf = Arrays.copyOfRange(array, lo, mid);
		for (int i = 0, j = lo, k = mid; i < buf.length; j++)
			array[j] = (k == hi || buf[i] < array[k]) ?
				buf[i++] : array[k++];
	}

	public static void main(String arg[]) {
		long arr[] = new long[100000];
		var rand = new Random();

		for(int i = 0; i < arr.length; i++)
			arr[i] = rand.nextLong();

		SortTask sort = new SortTask(arr);
		ForkJoinPool fork = new ForkJoinPool();

        long startTime = System.currentTimeMillis();
		Arrays.sort(arr);
        long endTime = System.currentTimeMillis();

        System.out.printf("Direct sort - Arrays of long of size %d sort took " + (endTime - startTime) + 
                " milliseconds.\n", arr.length);

        startTime = System.currentTimeMillis();
		fork.invoke(sort);
        endTime = System.currentTimeMillis();
/* 
		for(int i = 0; i < arr.length; i++)
			System.out.printf("Ele is %d\n", arr[i]);

*/
        System.out.printf("Arrays of long of size %d sort took " + (endTime - startTime) + 
                " milliseconds.\n", arr.length);

	}
}

