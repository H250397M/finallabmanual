// now for the file which will give us the out put
// the main block of code

public class Main {
    public static void main(String[] args) {
        System.out.println("=== 1 & 2: Circle ===");
        System.out.println("Count before: " + Circle.getCount());
        Circle c1 = new Circle();
        Circle c2 = new Circle(5.0);
        System.out.println("c1 radius = " + c1.getRadius());
        System.out.println("c2 radius = " + c2.getRadius());
        System.out.println("Count after: " + Circle.getCount());


        // let e1 , e2 be my alias for employee instead of repeartion
        System.out.println("\n 3: Employee ===");
        Employee e1 = new Employee("Micheal");
        Employee e2 = new Employee("LIonel", "IT");
        Employee e3 = new Employee("Kikido", "Finance", 1500.0);
        Employee e4 = new Employee(e3);     // copy constructor
        System.out.println(e1);
        System.out.println(e2);
        System.out.println(e3);
        System.out.println(e4);

        System.out.println("\n Challenge: Temperature ");
        System.out.println(Temperature.fromCelsius(100));
        System.out.println(Temperature.fromFahrenheit(32));
        System.out.println(Temperature.fromFahrenheit(98.6));
    }
}

// /n means breaking to a new line