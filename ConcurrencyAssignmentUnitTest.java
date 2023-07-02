public class ConcurrencyAssignmentUnitTest {
    public static void main(String[] args) {
        // Run unit test
        boolean testPassed = runUnitTest();

        // Print unit test result
        System.out.println("Unit Test Result: " + (testPassed ? "Passed" : "Failed"));
    }

    private static boolean runUnitTest() {
        // Create array for testing
        int[] numbers = {1, 2, 3, 4, 5};

        // Compute parallel sum
        long parallelSum = ConcurrencyAssignment.parallelArraySum(numbers);

        // Compute single thread sum
        long singleThreadSum = ConcurrencyAssignment.singleThreadArraySum(numbers);

        // Check that sums are equal
        return parallelSum == singleThreadSum;
    }
}
