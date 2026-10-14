// Exercise 1 (basic): Person is the superclass
public class Person {

    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    // Written once here; Student and Teacher inherit it without repeating the code
    public void display() {
        System.out.println("Name: " + name + ", Age: " + age);
    }
}