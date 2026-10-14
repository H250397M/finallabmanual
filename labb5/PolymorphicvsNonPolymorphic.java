class Parent {
    String type = "Parent Field";

    static void info() {
        System.out.println("Parent Static Method");
    }

    void display() {
        System.out.println("Parent Instance Method");
    }
}

class Child extends Parent {
    String type = "Child Field"; // EXPLAIN: Field hiding (NOT overriding)

    static void info() {
        System.out.println("Child Static Method"); // EXPLAIN: Method hiding (NOT overriding)
    }

    @Override
    void display() {
        System.out.println("Child Instance Method"); // EXPLAIN: Dynamic method overriding
    }
}

public class PolymorphicvsNonPolymorphic {
    public static void main(String[] args) {
        Parent ref = new Child(); // Upcasted reference

        // 1. Instance Method Call (Polymorphic)
        ref.display(); // Output: "Child Instance Method"

        // 2. Direct Field Access (NOT Polymorphic)
        System.out.println(ref.type); // Output: "Parent Field"

        // 3. Static Method Call (NOT Polymorphic)
        ref.info(); // Output: "Parent Static Method"
    }
}