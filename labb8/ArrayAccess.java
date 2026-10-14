public class ArrayAccess {
    public static void printElement(int[] numbers, int index) {
        try {
            // EXPLAIN: Throws ArrayIndexOutOfBoundsException if index is < 0 or >= length
            System.out.println("Element at index " + index + ": " + numbers[index]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Error: Index " + index + " is out of bounds for array length " + numbers.length);
        }
    }

    public static void main(String[] args) {
        int[] data = {10, 20, 30, 40, 50};

        printElement(data, 2);  // Valid access -> prints 30
        printElement(data, 10); // Out of bounds -> caught safely
        printElement(data, -1); // Out of bounds -> caught safely
    }
}
