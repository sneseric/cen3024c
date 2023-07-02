import java.util.Random;

public class ConcurrencyAssignment {
    private static final int ARRAY_SIZE = 200000000; 
    private static final int NUM_THREADS = Runtime.getRuntime().availableProcessors(); 

    public static void main(String[] args) {
        // Create random number array
        int[] numbers = generateRandomArray(ARRAY_SIZE);

        // Calculate parallel sum
        long startTime = System.currentTimeMillis();
        long parallelSum = parallelArraySum(numbers);
        long parallelTime = System.currentTimeMillis() - startTime;

        // Calculate single thread sum
        startTime = System.currentTimeMillis();
        long singleThreadSum = singleThreadArraySum(numbers);
        long singleThreadTime = System.currentTimeMillis() - startTime;

        // Print results
        System.out.println("Parallel Sum: " + parallelSum);
        System.out.println("Parallel Time: " + parallelTime + " ms");
        System.out.println("Single Thread Sum: " + singleThreadSum);
        System.out.println("Single Thread Time: " + singleThreadTime + " ms");
    }

    private static int[] generateRandomArray(int size) {
        int[] array = new int[size];
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(10) + 1; 
        }
        return array;
    }

    public static long parallelArraySum(int[] array) {
        int numElementsPerThread = array.length / NUM_THREADS;
        int remainingElements = array.length % NUM_THREADS;

        // Create partial sum array
        long[] partialSums = new long[NUM_THREADS];

        // Start threads
        Thread[] threads = new Thread[NUM_THREADS];
        for (int i = 0; i < NUM_THREADS; i++) {
            final int start = i * numElementsPerThread;
            final int end = (i == NUM_THREADS - 1) ? start + numElementsPerThread + remainingElements : start + numElementsPerThread;
            final int threadIndex = i; 

            threads[i] = new Thread(() -> {
                long sum = 0;
                for (int j = start; j < end; j++) {
                    sum += array[j];
                }
                partialSums[threadIndex] = sum;
            });

            threads[i].start();
        }

        try {
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Final sum
        long totalSum = 0;
        for (long partialSum : partialSums) {
            totalSum += partialSum;
        }

        return totalSum;
    }

    public static long singleThreadArraySum(int[] array) {
        long sum = 0;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        return sum;
    }
}
