public record Student(String name, int age) {

    // Compact constructor: no parameter list, runs BEFORE the fields are
    // assigned, so it is the place to validate.
    public Student {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative");
        }
    }
}

/*
 * ===================== NOTES: Student (record) =====================
 * Exercise 2 (Basic)
 *
 * What the compiler generates from  record Student(String name, int age)
 *    - private final fields 'name' and 'age'
 *    - a canonical constructor Student(String name, int age)
 *    - accessors name() and age()  (NOT getName(); records have no "get")
 *    - equals(), hashCode() and toString()
 *    - the class is implicitly final and the fields cannot be changed,
 *      so a record is an immutable data carrier with no setters.
 *
 * Generated behaviour
 *    - toString() prints  Student[name=Rudo, age=20]
 *    - equals() compares the COMPONENTS, so two records with the same
 *      name and age are equal even though they are different objects.
 *    - hashCode() is consistent with equals(), so records work as keys
 *      in a HashMap or members of a HashSet with no extra code.
 *
 * The compact constructor
 *    - Validation lives here so an invalid Student can never exist, the
 *      same idea as validating in the constructor in the encapsulation
 *      exercises.
 * ===================================================================
 */
