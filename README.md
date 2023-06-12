Fibonacci Documentation

Overview
The Fibonacci class provides methods for calculating Fibonacci numbers using both recursive and iterative approaches. It also includes a method to create and display a runtime chart comparing the two approaches.

Usage
To use the Fibonacci class, follow these steps:

1. Import the required libraries:
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

2. Create an instance of the Fibonacci class and call the desired methods.

3. Run the program to see the results and the runtime chart.

Methods:
public static long fibonacciRecursive(int n)
Calculates the Fibonacci number at the specified index n using a recursive approach.
Parameters
n - The index of the Fibonacci number to calculate.
Returns
The Fibonacci number at index n.

public static long fibonacciIterative(int n)
Calculates the Fibonacci number at the specified index n using an iterative approach.
Parameters
n - The index of the Fibonacci number to calculate.
Returns
The Fibonacci number at index n.

public static void createChart(long recursiveRuntime, long iterativeRuntime)
Creates a bar chart comparing the runtimes of the recursive and iterative approaches.
Parameters
recursiveRuntime - The runtime of the recursive approach in nanoseconds.
iterativeRuntime - The runtime of the iterative approach in nanoseconds.

public static void main(String[] args)
The main method of the Fibonacci class. It demonstrates the usage of the Fibonacci methods and displays the runtime chart.

Dependencies
This code requires the JFreeChart library to create and display the chart.

Compatibility
This code is written in Java and requires JDK 11 or above.

References
JFreeChart documentation: https://www.jfree.org/jfreechart/
