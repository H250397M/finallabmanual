public class Address {
    String city;

    Address(String city) {
        this.city = city;
    }

    public static void main(String[] args) {

        // Person with an address
        Address addr = new Address("Harare");
        Person p1 = new Person("Lionel", addr);

        // Chained dot expression
        System.out.println("City: " + p1.address.city);

        // Person without an address
        Person p2 = new Person("John", null);

        // This will cause a NullPointerException
        System.out.println("City: " + p2.address.city);
    }
}

class Person {
    String name;
    Address address;

    Person(String name, Address address) {
        this.name = name;
        this.address = address;
    }
}