import java.util.InputMismatchException;
import java.util.Scanner;

public class DivisionHandler {
    public static void main(String[] args) {
        // EXPLAIN: try-with-resources automatically closes the Scanner resource when finished
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter numerator (integer): ");
            int num = scanner.nextInt();

            System.out.print("Enter denominator (integer): ");
            int den = scanner.nextInt();

            int result = num / den; // EXPLAIN: Throws ArithmeticException if den == 0
            System.out.println("Result: " + num + " / " + den + " = " + result);

        } catch (InputMismatchException e) {
            // EXPLAIN: Handles non-integer user input
            System.out.println("Error: Please enter valid integer numbers only.");
        } catch (ArithmeticException e) {
            // EXPLAIN: Handles division by zero
            System.out.println("Error: Cannot divide by zero.");
        } finally {
            System.out.println("Division attempt complete.");
        }
    }
}