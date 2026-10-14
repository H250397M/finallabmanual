
//
public class Employee {
    private static int nextId = 1;          //  now auto-incrementing counter

    private final int employeeId;
    private String name;                   // private was used as an acsess modifier so it cannot be accsesd anywhere
    private String department;
    private double salary;

    // Constructor 1: name only but it is not assigned
    public Employee(String name) {
        this(name, "Unassigned", 0.0);
    }

    // Constructor 2: name + department
    public Employee(String name, String department) {
        this(name, department, 0.0);
    }

    // Constructor 3: full constructor (all others chain here)
    public Employee(String name, String department, double salary) {
        this.employeeId = nextId++;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }


    public Employee(Employee other) {
        this(other.name, other.department, other.salary);
    }

    public int getEmployeeId() { return employeeId; }

    @Override
    public String toString() {
        return "Employee{id=" + employeeId + ", name=" + name +
                ", dept=" + department + ", salary=" + salary + "}";
    }
}
// once again this does not run by it self there is also no main method