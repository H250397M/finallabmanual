
import java.util.ArrayList;

// Exercise 5 (challenge): refactor "Stack extends ArrayList" into "Stack has-a ArrayList"

// BAD DESIGN: inheritance. A BadStack IS-A ArrayList, so it inherits ALL of ArrayList's public methods.
class BadStack extends ArrayList<Integer> {
    void push(int x) { add(x); }
    int pop() { return remove(size() - 1); }
}

// GOOD DESIGN: composition. The list is a private detail; only stack operations are exposed.
class GoodStack {
    private final ArrayList<Integer> items = new ArrayList<>();   // HAS-A

    void push(int x) { items.add(x); }

    int pop() {
        if (items.isEmpty()) throw new IllegalStateException("Stack is empty");
        return items.remove(items.size() - 1);
    }

    int peek() {
        if (items.isEmpty()) throw new IllegalStateException("Stack is empty");
        return items.get(items.size() - 1);
    }

    boolean isEmpty() { return items.isEmpty(); }
    int size() { return items.size(); }
}

public class StackDemo {
    public static void main(String[] args) {
        BadStack bad = new BadStack();
        bad.push(1);
        bad.push(2);
        bad.push(3);

        // The inheritance version wrongly exposes list operations that break the LIFO rule:
        bad.add(0, 99);        // insert at the BOTTOM of the stack
        bad.remove(1);         // remove from the MIDDLE of the stack
        bad.set(0, -5);        // overwrite any element
        System.out.println("BadStack after illegal operations: " + bad);

        GoodStack good = new GoodStack();
        good.push(1);
        good.push(2);
        good.push(3);
        // good.add(0, 99);    // does not compile: GoodStack has no add() method
        System.out.println("GoodStack peek: " + good.peek());
        System.out.println("GoodStack pop: " + good.pop());
        System.out.println("GoodStack size: " + good.size());

        /*
         * What the inheritance version wrongly exposes:
         *  - add(index, e), remove(index), set(index, e), get(index), addAll, sort, clear, iterator...
         *    Anyone can insert or remove in the middle or bottom, so the "last in, first out" guarantee is gone.
         *  - It is not a true "is-a": a Stack is not an ArrayList, it merely uses one to store data.
         *  - It locks the design to ArrayList forever; with composition you could switch to a LinkedList
         *    or an array without changing any code that uses GoodStack.
         * (This mistake is real: java.util.Stack extends Vector and still exposes these methods.)
         */
    }
}