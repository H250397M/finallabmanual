// Exercise 5 (challenge): two interfaces with the same default method
interface Friendly {
    default void hello() { System.out.println("Hey there!"); }
}

interface Formal {
    default void hello() { System.out.println("Good day to you."); }
}

// Without the hello() override below this class would NOT compile:
//   "class Receptionist inherits unrelated defaults for hello() from types Friendly and Formal"
// The compiler refuses to guess which one we want, so we must override hello() ourselves.
class Receptionist implements Friendly, Formal {
    @Override
    public void hello() {
        Friendly.super.hello();      // InterfaceName.super.method() picks one specific interface's default
        Formal.super.hello();        // we can call one, the other, or both (or write something brand new)
    }
}

public class HelloConflictDemo {
    public static void main(String[] args) {
        new Receptionist().hello();
    }
}

/*
 * THE RULE (how Java resolves default-method conflicts):
 *  1. A method declared in the class (or inherited from a superclass) always wins over interface defaults.
 *  2. If one interface extends the other, the more specific (sub) interface's default wins.
 *  3. Otherwise, if two unrelated interfaces provide the same default method, the class MUST override it
 *     (compile error if it does not). Inside the override, InterfaceName.super.hello() chooses which
 *     interface's version to call.
 * Why: Java wants no ambiguity, so rather than silently picking one (the "diamond problem") it makes
 * the programmer decide explicitly.
 */
