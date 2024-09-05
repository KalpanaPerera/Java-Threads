import java.util.ArrayList;
import java.util.List;

public class SummationWithThreads {
    private static final int NUM_THREADS = 10;
    private static final int RANGE = 10_000_000;

    static class SumTask implements Runnable {
        private long start;
        private long end;
        private long result;

        public SumTask(long start, long end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            result = 0;
            for (long i = start; i <= end; i++) {
                result += i;
            }
        }

        public long getResult() {
            return result;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();

        List<Thread> threads = new ArrayList<>();
        List<SumTask> tasks = new ArrayList<>();

        long chunkSize = RANGE / NUM_THREADS;

        for (int i = 0; i < NUM_THREADS; i++) {
            long start = i * chunkSize + 1;
            long end = (i + 1) * chunkSize;
            if (i == NUM_THREADS - 1) {
                end = RANGE;
            }

            SumTask task = new SumTask(start, end);
            tasks.add(task);
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        long totalSum = 0;
        for (int i = 0; i < NUM_THREADS; i++) {
            threads.get(i).join();
            totalSum += tasks.get(i).getResult();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Summation with threads: " + totalSum);
        System.out.println("Execution time with threads: " + (endTime - startTime) + " milliseconds");
    }
}
