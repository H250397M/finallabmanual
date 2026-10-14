
// Exercises 1 and 2: run this file
public class PersonDemo {
    public static void main(String[] args) {
        Person p = new Person("Farai", 40);
        Student s = new Student("Rudo", 20, "Computer Science");
        Teacher t = new Teacher("Mr Moyo", 45, "Mathematics");

        // Exercise 1: display() is inherited by Student, so we never wrote it in Student
        p.display();
        s.display();
        System.out.println("Course: " + s.getCourse());

        // Exercise 2: Teacher inherits the very same display(); Student and Teacher share the Person code
        t.display();
        System.out.println("Subject: " + t.getSubject());

        // Both are Persons, so one method can accept either (an "is-a" relationship)
        System.out.println("--- shared code through Person ---");
        showPerson(s);
        showPerson(t);
    }

    static void showPerson(Person person) {
        person.display();
    }
}