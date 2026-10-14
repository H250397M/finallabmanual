
// Exercise 1: Student is  A Person and adds a course
public class Student extends Person {

    private String course; // acsses modifier

    public Student(String name, int age, String course) {
        super(name, age);          // Person's constructor sets name and age
        this.course = course;
    }

    public String getCourse() { return course; }
}