import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Fibonacci {
    public static long fibonacciRecursive(int n) {
        if (n <= 1)
            return n;
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    public static long fibonacciIterative(int n) {
        if (n <= 1)
            return n;

        long fib = 0;
        long prev1 = 0;
        long prev2 = 1;

        for (int i = 2; i <= n; i++) {
            fib = prev1 + prev2;
            prev1 = prev2;
            prev2 = fib;
        }

        return fib;
    }

    public static void createChart(long recursiveRuntime, long iterativeRuntime) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(recursiveRuntime, "Runtime", "Recursive");
        dataset.addValue(iterativeRuntime, "Runtime", "Iterative");

        JFreeChart chart = ChartFactory.createBarChart("Fibonacci Runtimes", "Approach", "Runtime (ns)",
                dataset, PlotOrientation.VERTICAL, true, true, false);

        ChartFrame frame = new ChartFrame("Fibonacci Runtimes", chart);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        int n = 10;

        // Recursive Fibonacci
        long startTimeRecursive = System.nanoTime();
        for (int i = 0; i < n; i++) {
            long resultRecursive = fibonacciRecursive(i);
            System.out.print(resultRecursive + " ");
        }
        long endTimeRecursive = System.nanoTime();
        long durationRecursive = endTimeRecursive - startTimeRecursive;

        System.out.println("\nRecursive Fibonacci Runtime: " + durationRecursive + " nanoseconds");

        // Iterative Fibonacci
        long startTimeIterative = System.nanoTime();
        for (int i = 0; i < n; i++) {
            long resultIterative = fibonacciIterative(i);
            System.out.print(resultIterative + " ");
        }
        long endTimeIterative = System.nanoTime();
        long durationIterative = endTimeIterative - startTimeIterative;

        System.out.println("\nIterative Fibonacci Runtime: " + durationIterative + " nanoseconds");

        // Create and display the chart
        createChart(durationRecursive, durationIterative);
    }
}




