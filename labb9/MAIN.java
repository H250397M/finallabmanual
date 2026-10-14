import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MAIN {

    // A deliberately BROKEN class: equals() without hashCode().
    // Used only to show the problem the lab warns about.
    private static class BrokenBook {
        private final String isbn;
        BrokenBook(String isbn) { this.isbn = isbn; }

        @Override
        public boolean equals(Object o) {
            return o instanceof BrokenBook other && isbn.equals(other.isbn);
        }
        // no hashCode() override: it keeps Object's identity-based hash code
    }

    public static void main(String[] args) {


        // EXERCISE 1 OUTPUT
        System.out.println("=== Exercise 1: enum Level and switch ===");
        for (Level level : Level.values()) {
            LevelAdvice.printAdvice(level);
        }
        System.out.println("Switch expression: " + LevelAdvice.adviceFor(Level.HIGH));
        System.out.println("Level.LOW == Level.LOW ? " + (Level.LOW == Level.LOW));
        System.out.println("Level.valueOf(\"MEDIUM\") ordinal = " + Level.valueOf("MEDIUM").ordinal());


        // EXERCISE TWO
        System.out.println("\n=== Exercise 2: record Student ===");
        Student s1 = new Student("Rudo", 20);
        Student s2 = new Student("Rudo", 20);
        Student s3 = new Student("Tariro", 21);
        System.out.println(s1);                                   // generated toString
        System.out.println("s1 == s2      : " + (s1 == s2));      // false: different objects
        System.out.println("s1.equals(s2) : " + s1.equals(s2));   // true: same components
        System.out.println("s1.equals(s3) : " + s1.equals(s3));
        System.out.println("hash codes equal: " + (s1.hashCode() == s2.hashCode()));
        System.out.println("Component name(): " + s1.name() + ", age(): " + s1.age());
        try {
            new Student("Tendai", -1);
        } catch (IllegalArgumentException e) {
            System.out.println("Compact constructor rejected: " + e.getMessage());
        }


        // EXERCISE 3 OUTPUT
        System.out.println("\n=== Exercise 3: Book equals/hashCode in a HashSet ===");
        Book b1 = new Book("978-0134685991", "Effective Java");
        Book b2 = new Book("978-0134685991", "Effective Java (2nd copy)");   // same ISBN
        Book b3 = new Book("978-0596009205", "Head First Java");
        Set<Book> books = new HashSet<>();
        System.out.println("add(b1) returned " + books.add(b1));
        System.out.println("add(b2) returned " + books.add(b2) + "   <- duplicate ISBN rejected");
        System.out.println("add(b3) returned " + books.add(b3));
        System.out.println("Set size: " + books.size());
        System.out.println("b1.equals(b2): " + b1.equals(b2)
            + ", same hash: " + (b1.hashCode() == b2.hashCode()));

        Set<BrokenBook> broken = new HashSet<>();
        broken.add(new BrokenBook("111"));
        broken.add(new BrokenBook("111"));
        System.out.println("Broken class (equals but no hashCode), set size: " + broken.size()
            + "   <- duplicate NOT rejected");
        System.out.println("contains(equal BrokenBook): "
            + broken.contains(new BrokenBook("111")) + "   <- usually false");


            // THE FOURTH EXERCISE OUTPUT 
        System.out.println("\n=== Exercise 4: LinkedList with nested Node and inner Iterator ===");
        LinkedList<String> list = new LinkedList<>();
        list.add("alpha");
        list.add("beta");
        list.add("gamma");
        System.out.println("size = " + list.size() + ", list = " + list);
        System.out.print("for-each: ");
        for (String item : list) {
            System.out.print(item + " ");
        }
        System.out.println();
        System.out.print("Two loops at once, pairs: ");
        for (String a : list) {
            for (String b : list) {
                if (a.equals("alpha")) System.out.print(a.charAt(0) + "" + b.charAt(0) + " ");
            }
        }
        System.out.println("  (independent iterators)");
        LinkedList<Integer> empty = new LinkedList<>();
        System.out.println("Empty list: " + empty + ", isEmpty = " + empty.isEmpty());
        try {
            empty.iterator().next();
        } catch (java.util.NoSuchElementException e) {
            System.out.println("next() past the end throws NoSuchElementException");
        }


        // THE OUTPUT FOR THE FIFTH OUTPUT
        System.out.println("\n=== Exercise 5: Coin, fewest coins ===");
        int[] amounts = {0, 1, 30, 41, 99, 100};
        for (int amount : amounts) {
            Map<Coin, Integer> change = Coin.fewestCoins(amount);
            System.out.println(amount + " cents -> " + Coin.totalCoins(change)
                + " coins " + change);
        }
        try {
            Coin.fewestCoins(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Negative amount rejected: " + e.getMessage());
        }
    }
}

// this is the main folder which execute all the exercises at once 
