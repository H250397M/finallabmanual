
// Exercise 4 (intermediate): Comparable (natural order by name) plus a Comparator (order by marks)
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

class Student implements Comparable<Student> {
    private final String name;
    private final int marks;

    Student(String name, int marks) { this.name = name; this.marks = marks; }

    public String getName() { return name; }
    public int getMarks() { return marks; }

    // natural ordering: alphabetical by name. A class can have only ONE natural ordering.
    @Override
    public int compareTo(Student other) {
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString() { return name + "(" + marks + ")"; }
}

// A Comparator is a separate object, so we can have as many orderings as we like without touching Student.
class MarksComparator implements Comparator<Student> {
    @Override
    public int compare(Student a, Student b) {
        return Integer.compare(b.getMarks(), a.getMarks());    // highest marks first
    }
}

public class StudentSortDemo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Tafara", 75));
        students.add(new Student("Anesu", 68));
        students.add(new Student("Mercy", 91));

        Collections.sort(students);                         // uses compareTo -> by name
        System.out.println("By name:  " + students);

        Collections.sort(students, new MarksComparator());  // uses the Comparator -> by marks
        System.out.println("By marks: " + students);

        // The same Comparator written as a lambda (shorter, since Comparator has one abstract method):
        students.sort((a, b) -> Integer.compare(a.getMarks(), b.getMarks()));   // lowest marks first
        System.out.println("Lowest first (lambda): " + students);
    }
}