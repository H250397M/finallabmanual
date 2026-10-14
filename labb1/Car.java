// I start with the public class Car then I set my variables
public class Car {

    String brand; // A brand is a word so it comes as a String
    int year; // A year is a whole number so I'm setting it as an int
    double mileage; // Mileage can have a fraction so I use double

    // Constructor was created here so I can initialize objects
    // and set their values when they are created
    Car(String brand, int year, double mileage) {
        this.brand = brand;
        this.year = year;
        this.mileage = mileage;
    }

    // Display method for the brand, year and mileage
    void display() {
        System.out.println("Brand: " + brand);
        System.out.println("Year: " + year);
        System.out.println("Mileage: " + mileage);
        System.out.println("Antique: " + isAntique());
        System.out.println();
    }

    // The second question in our basic chapter
    // Checks if the car is more than 25 years old
    boolean isAntique() {
        return (2026 - year) > 25;
    }

    public static void main(String[] args) {

        Car car1 = new Car("Toyota GD 6", 2020, 50045);
        Car car2 = new Car("Honda CIVIC", 1980, 644544);
        Car car3 = new Car("BMW X5 COMPETITION", 2022, 45884);

        car1.display();
        car2.display();
        car3.display();
    }
}