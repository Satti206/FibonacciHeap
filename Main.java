import java.util.*;

public class Main {

    public static void main(String[] args) {
        int n = 10000;
        int[] array = generateRandomArray(n);

        FibonacciHeap<Integer> heap = new FibonacciHeap<>();
        long start, end, time;
        int operations;

        // Add elements to the heap and measure running time and number of operations
        System.out.println("Adding elements to the heap:");

        time = 0;
        operations = 0;
        for (int i = 0; i < n; i++) {
            start = System.nanoTime();
            heap.insert(array[i]);
            end = System.nanoTime();
            time += end - start;
            operations+= heap.COUNT_OF_OPERATIONS;
            heap.COUNT_OF_OPERATIONS = 0;
        }
        System.out.println("Time: " + time/n + " ns");
        System.out.println("Operations: " + operations/n);

        // Search for 100 random elements in the heap and measure running time and number of operations

        operations = 0;
        for (int i = 0; i < 100; i++) {
            start = System.nanoTime();
            heap.removeMin();
            end = System.nanoTime();
            time += end - start;
            operations += heap.COUNT_OF_OPERATIONS;
            heap.COUNT_OF_OPERATIONS = 0;
        }


        System.out.println("Time: " + time/100 + " ns");
        System.out.println("Operations: " + operations/100);
    }

    private static int[] generateRandomArray(int n) {
        int[] array = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            array[i] = rand.nextInt();
        }
        return array;
    }
}
