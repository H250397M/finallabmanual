
// Exercise 4 (intermediate): why "class Dog extends Animal" fails when Animal only has Animal(String name)
class Animal {
    protected String name;

    Animal(String name) {          // the ONLY constructor: there is no no-argument constructor
        this.name = name;
    }
}

/*
 * BROKEN VERSION (uncomment it to see the compile error in IntelliJ):
 *
 * class Dog extends Animal {
 *     Dog() {
 *         System.out.println("Dog created");
 *     }
 * }
 *
 * Why it fails: a subclass constructor must call a superclass constructor first. Because Dog() does
 * not write super(...) itself, the compiler inserts super() automatically. But Animal has no
 * Animal() constructor (declaring Animal(String) removed the free default one), so the compiler reports:
 *     "Implicit super constructor Animal() is undefined. Must explicitly invoke another constructor"
 * The same error appears if Dog has no constructor at all, because the default constructor also calls super().
 */

// FIX: call the existing constructor explicitly with super(name)
class Dog extends Animal {
    Dog(String name) {
        super(name);               // must be the first statement; matches Animal(String)
        System.out.println("Dog created: " + this.name);
    }
}
// Another valid fix: Dog() { super("Unknown"); }  -- or add an Animal() constructor to the superclass.

public class AnimalDemo {
    public static void main(String[] args) {
        new Dog("Rex");
    }
}