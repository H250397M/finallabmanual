import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

    // STATIC NESTED class: a Node needs no reference to any list object.
    // Private, because it is an implementation detail nobody outside needs.
    private static class Node<T> {
        private final T value;
        private Node<T> next;

        Node(T value) {
            this.value = value;
        }
    }

    private Node<T> head;                   // first node
    private Node<T> tail;                   // last node, so add() is O(1)
    private int size;

    public void add(T value) {
        Node<T> node = new Node<>(value);
        if (head == null) {
            head = node;
            tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public int size()        { return size; }
    public boolean isEmpty() { return size == 0; }

    // Iterable requires this method. It is what makes for-each work.
    @Override
    public Iterator<T> iterator() {
        return new ListIterator();          // 'this' list is passed implicitly
    }

    // INNER (non-static) class: every ListIterator holds a hidden
    // reference to the LinkedList that created it, and reads its 'head'.
    private class ListIterator implements Iterator<T> {
        private Node<T> current = head;     // 'head' belongs to the OUTER list

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public T next() {
            if (current == null) {
                throw new NoSuchElementException();
            }
            T value = current.value;
            current = current.next;
            return value;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (T value : this) {              // our own for-each
            if (sb.length() > 1) sb.append(", ");
            sb.append(value);
        }
        return sb.append("]").toString();
    }
}

