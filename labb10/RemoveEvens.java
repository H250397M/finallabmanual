// FORR EXERCISE 2


import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RemoveEvens {
    public static void main(String[] args) {

        // A Predicate<Integer> is a lambda that answers true or false.
        Predicate<Integer> isEven = n -> n % 2 == 0;

        // new ArrayList<>(...) makes a MODIFIABLE copy. List.of(...) alone is immutable.
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
        System.out.println("Before: " + numbers);

        boolean changed = numbers.removeIf(isEven);     // removes every element where the test is true
        System.out.println("After : " + numbers + "   (removeIf returned " + changed + ")");

        // Zero and negative numbers: -4 % 2 == 0 and 0 % 2 == 0, so they count as even.
        List<Integer> mixed = new ArrayList<>(List.of(-4, -3, 0, 7, 12));
        mixed.removeIf(isEven);
        System.out.println("Mixed : " + mixed);

        // Nothing to remove: the list is unchanged and removeIf returns false.
        List<Integer> allOdd = new ArrayList<>(List.of(1, 3, 5));
        System.out.println("All odd, removeIf returned " + allOdd.removeIf(isEven) + ", list " + allOdd);

        // The same predicate, reversed with negate(): keep only the evens.
        List<Integer> keepEvens = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6));
        keepEvens.removeIf(isEven.negate());
        System.out.println("negate(): " + keepEvens);

        // Common mistake: removeIf on an immutable list
        List<Integer> fixed = List.of(1, 2, 3, 4);
        try {
            fixed.removeIf(isEven);
        } catch (UnsupportedOperationException e) {
            System.out.println("List.of(...) is immutable: removeIf threw UnsupportedOperationException");
        }
    }
}


