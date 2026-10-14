// now this is the file that will run and execute

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EncapsulationDemo {
    public static void main(String[] args) {

        System.out.println(" Tasks 1 & 2: Student ");
        Student s = new Student("leo", 72);
        System.out.println(s);
        s.setMarks(45);
        System.out.println("After setMarks(45): " + s);
        s.setMarks(80);
        System.out.println("After setMarks(80): " + s + "  (grade recalculated, not stored)");

        try {
            s.setMarks(101);
        } catch (IllegalArgumentException e) {
            System.out.println("Setter rejected 101: " + e.getMessage());
        }
        try {
            new Student("Tendai", -5);
        } catch (IllegalArgumentException e) {
            System.out.println("Constructor rejected -5: " + e.getMessage());
        }
        System.out.println("Marks still valid after rejections: " + s.getMarks());

        System.out.println("Grade boundaries:");
        int[] samples = {100, 70, 69, 60, 59, 50, 49, 0};
        for (int m : samples) {
            System.out.println("  marks " + m + " -> " + new Student("Test", m).getGrade());
        }


        // for my third assignment
        System.out.println("\n=== Task 3: BankAccount history ===");
        BankAccount acc = new BankAccount("ACC-001", "Tariro", 100.00);
        acc.deposit(50.00);
        acc.withdraw(30.00);
        try {
            acc.withdraw(500.00);
        } catch (IllegalStateException e) {
            System.out.println("Overdraft rejected: " + e.getMessage());
        }

        List<String> snapshot = acc.getTransactionHistory();
        System.out.println("History (" + snapshot.size() + " entries):");
        for (String entry : snapshot) {
            System.out.println("  " + entry);
        }
        try {
            snapshot.add("FAKE DEPOSIT 1000000.00");
        } catch (UnsupportedOperationException e) {
            System.out.println("Tampering blocked: history list is unmodifiable");
        }
        acc.deposit(10.00);
        System.out.println("Old snapshot size: " + snapshot.size()
                + ", fresh copy size: " + acc.getTransactionHistory().size());
        System.out.println("Balance: " + acc.getBalance());



        // the fourth oone 
        System.out.println("\n=== Task 4: ImmutablePerson ===");
        List<String> hobbies = new ArrayList<>(List.of("chess", "football"));
        Immutableperson p = new Immutableperson("Tariro", LocalDate.of(2005, 3, 15), hobbies);
        System.out.println(p);

        hobbies.add("gaming");   // change the caller's own list afterwards
        System.out.println("Caller's list now:  " + hobbies);
        System.out.println("Person's hobbies:   " + p.getHobbies() + "  (unaffected)");

        try {
            p.getHobbies().add("hacking");
        } catch (UnsupportedOperationException e) {
            System.out.println("getHobbies().add() blocked: list is unmodifiable");
        }

        Immutableperson renamed = p.withName("Rudo");
        System.out.println("Original: " + p.getName() + ", renamed copy: " + renamed.getName());
        System.out.println("Same data equal? "
                + p.equals(new Immutableperson("Tariro", LocalDate.of(2005, 3, 15),
                List.of("chess", "football"))));

        try {
            new Immutableperson(" ", LocalDate.of(2005, 3, 15), List.of());
        } catch (IllegalArgumentException e) {
            System.out.println("Constructor rejected blank name: " + e.getMessage());
        }
    }
}

