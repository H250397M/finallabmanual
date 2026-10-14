// 4TH WORK

import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

public class AboveAverage {

    record Student(String name, double marks) {}

    // Names of students whose marks are ABOVE the class average, A to Z.
    static List<String> aboveAverage(List<Student> students) {
        double average = students.stream()
                .mapToDouble(Student::marks)
                .average()
                .orElse(0);                                 // an empty list has no average

        return students.stream()
                .filter(s -> s.marks() > average)           // strictly above the average
                .map(Student::name)                         // Student  String
                .sorted(String.CASE_INSENSITIVE_ORDER)      // alphabetical, ignoring case
                .toList();                                  // terminal operation: runs the pipeline
    }

    public static void main(String[] args) {
        List<Student> students = List.of(
            new Student("Tariro", 85),
            new Student("Farai", 62),
            new Student("Rudo", 91),
            new Student("Kuda", 55),
            new Student("Ada", 78),
            new Student("Ben", 70));

        double average = students.stream().mapToDouble(Student::marks).average().orElse(0);
        System.out.println(String.format(Locale.US, "Class average: %.2f", average));

        System.out.println("Above average (sorted):");
        aboveAverage(students).forEach(name -> System.out.println("  " + name));

        // Streams do not modify their source.
        System.out.println("Original list still has " + students.size() + " students");

        // Edge cases
        System.out.println("Empty list      -> " + aboveAverage(List.of()));
        System.out.println("Everyone equal  -> " + aboveAverage(List.of(
            new Student("A", 50), new Student("B", 50))) + "  (nobody is ABOVE the average)");

        // Common mistake: reusing a stream after a terminal operation
        Stream<String> names = students.stream().map(Student::name);
        System.out.println("First use, count = " + names.count());
        try {
            names.count();                                  // the stream is already consumed
        } catch (IllegalStateException e) {
            System.out.println("Second use threw IllegalStateException: " + e.getMessage());
        }
    }
}


