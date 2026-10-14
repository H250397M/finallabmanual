class Animal {
    void sound() {
        System.out.println("Animal makes a generic sound");
    }
}

class Cat extends Animal {
    @Override
    void sound() {
        System.out.println("Cat says: Meow");
    }
}

class Cow extends Animal {
    @Override
    void sound() {
        System.out.println("Cow says: Moo");
    }
}

class Duck extends Animal {
    @Override
    void sound() {
        System.out.println("Duck says: Quack");
    }
}

public class AnimalPolymorphism {
    public static void main(String[] args) {
        // EXPLAIN: Upcasting — superclass array holding subclass instances
        Animal[] farm = { new Cat(), new Cow(), new Duck(), new Animal() };

        for (Animal a : farm) {
            // EXPLAIN: Dynamic Method Dispatch — JVM invokes the overridden method
            // based on the ACTUAL runtime object, not the declared variable type (Animal)
            a.sound();
        }
    }
}