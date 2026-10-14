// Exercise 2 (basic): an interface for unrelated classes
interface Flyable {
    void fly();     // implicitly public abstract
}

// Bird and Aeroplane share NO superclass (both just extend Object). The only thing they have in common
// is the capability to fly, which is exactly what an interface describes.
class Bird implements Flyable {
    @Override
    public void fly() { System.out.println("The bird flaps its wings and flies"); }
}

class Aeroplane implements Flyable {
    @Override
    public void fly() { System.out.println("The aeroplane takes off using its jet engines"); }
}

public class FlyableDemo {
    public static void main(String[] args) {
        Flyable[] flyers = { new Bird(), new Aeroplane() };   // program to the interface

        for (Flyable f : flyers) {
            f.fly();
        }
    }
}