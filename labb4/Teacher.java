// Exercise 2: Teacher IS-A Person and adds a subject. It reuses exactly the same Person code as Student
public class Teacher extends Person {

    private String subject;

    public Teacher(String name, int age, String subject) {
        super(name, age);
        this.subject = subject;
    }

    public String getSubject() { return subject; }
}