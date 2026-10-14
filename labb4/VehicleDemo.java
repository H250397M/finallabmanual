// Exercise 3 (intermediate): multilevel inheritance Vehicle -> Car -> ElectricCar.
// The classes are nested inside VehicleDemo so they do not clash with the Car class from Program 4.3
// when both are in the same IntelliJ source folder.
public class VehicleDemo {

    static class Vehicle {
        Vehicle() { System.out.println("1. Vehicle constructor"); }   // runs first (after Object)
    }

    static class Car extends Vehicle {
        Car() {                                                        // implicit super() -> Vehicle()
            System.out.println("2. Car constructor");
        }
    }

    static class ElectricCar extends Car {
        ElectricCar() {                                                // implicit super() -> Car() -> Vehicle()
            System.out.println("3. ElectricCar constructor");
        }
    }

    public static void main(String[] args) {
        System.out.println("Creating an ElectricCar...");
        new ElectricCar();
        // Trace: new ElectricCar() -> calls Car() -> calls Vehicle() -> calls Object().
        // Object finishes, then Vehicle's body, then Car's body, then ElectricCar's body.
    }
}