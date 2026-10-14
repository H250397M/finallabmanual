public class FinallyReturnDemo {
    public static int testMethod() {
        try {
            System.out.println("1. Inside try block - preparing to return 10");
            return 10; // EXPLAIN: Return value (10) is evaluated, but execution jumps to finally first
        } finally {
            // EXPLAIN: The finally block ALWAYS executes before method exit, even after return!
            System.out.println("2. Inside finally block - running cleanup");
        }
    }

    public static int testOverrideReturn() {
        try {
            return 100;
        } finally {
            // EXPLAIN: Returning inside finally overrides the try block's return value (anti-pattern!)
            return 200;
        }
    }

    public static void main(String[] args) {
        System.out.println("=== Test 1 ===");
        int val = testMethod();
        System.out.println("Method returned: " + val);

        System.out.println("\n=== Test 2 ===");
        int overriddenVal = testOverrideReturn();
        System.out.println("Method returned: " + overriddenVal);
    }
}
