public class Student {
    private final String name;              // never changes, so final and NO setter
    private int marks;                      // can change, so it gets a setter becaus eits a private method

    public Student(String name, int marks) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        this.name = name;
        this.marks = validateMarks(marks);  // validate in the constructor too
    }

    public String getName() {
        return name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = validateMarks(marks);  // same rule as the constructor
    }

    // the allocation i going to use the Zimsec standard: A 70-100, B 60-69, C 50-59, F 0-49.
    public String getGrade() {
        if (marks >= 70) return "A";  // i prefred if statements over the switch ones
        if (marks >= 60) return "B";
        if (marks >= 50) return "C";
        return "F";
    }


    // i then threw an exception if any errors occurs
    // no over 100 and less then zero
    private static int validateMarks(int marks) {
        if (marks < 0 || marks > 100) {
            throw new IllegalArgumentException(
                    "Marks must be between 0 and 100, but was " + marks);
        }
        return marks;
    }

    @Override
    public String toString() {
        return "Student{name=" + name + ", marks=" + marks + ", grade=" + getGrade() + "}";
    }
}

// so both task 1 and 2 are in this file altogether

