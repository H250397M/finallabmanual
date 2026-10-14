import java.util.Arrays;

public class OverloadDemo {
    // Compile-time polymorphism (Overloading)
    // Same method name, different parameter lists within the same class.

    void print(int val) {
        System.out.println("Printing int: " + val);
    }

    void print(double val) {
        System.out.println("Printing double: " + val);
    }

    void print(String val) {
        System.out.println("Printing String: " + val);
    }

    void print(int[] val) {
        System.out.println("Printing int array: " + Arrays.toString(val));
    }

    public static void main(String[] args) {
        OverloadDemo demo = new OverloadDemo();

        // Compiler decides which method to call at compile time based on arguments
        demo.print(42);
        demo.print(3.14159);
        demo.print("Hello Polymorphism");
        demo.print(new int[]{1, 2, 3, 4, 5});
    }
}