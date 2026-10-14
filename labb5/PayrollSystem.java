class Employee {
    private String name;
    private double baseSalary;

    public Employee(String name, double baseSalary) {
        this.name = name;
        this.baseSalary = baseSalary;
    }

    public String getName() { return name; }
    public double getBaseSalary() { return baseSalary; }

    public double calculatePay() {
        return baseSalary;
    }
}

class Manager extends Employee {
    private double bonus;

    public Manager(String name, double baseSalary, double bonus) {
        super(name, baseSalary);
        this.bonus = bonus;
    }

    @Override
    public double calculatePay() {
        return super.calculatePay() + bonus;
    }
}

// EXPLAIN: Adding a new subclass (Intern) without changing existing calculation loops
class Intern extends Employee {
    private double stipend;

    public Intern(String name, double stipend) {
        super(name, 0.0); // Base salary is 0, paid strictly by stipend
        this.stipend = stipend;
    }

    @Override
    public double calculatePay() {
        return stipend;
    }
}

public class PayrollSystem {
    public static void main(String[] args) {
        Employee[] staff = {
                new Employee("Kudzai", 2000),
                new Manager("Nyasha", 3500, 800),
                new Intern("Chelsea", 600) // Newly added subtype
        };

        double totalPayroll = 0;

        // Open/Closed Principle — This loop processes any present or future
        // subclass of Employee without needing any code changes.
        for (Employee e : staff) {
            double pay = e.calculatePay();
            System.out.printf("%s earns: $%.2f%n", e.getName(), pay);
            totalPayroll += pay;
        }

        System.out.printf("%nTotal Payroll: $%.2f%n", totalPayroll);
    }
}
