
// notice this will not run becuse it does not have a main method
// imports for dates etc
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public final class Immutableperson {
    private final String name;                             //  private final fields
    private final LocalDate dateOfBirth;
    private final List<String> hobbies;

    public Immutableperson(String name, LocalDate dateOfBirth, List<String> hobbies) {
        if (name == null || name.isBlank()) {              // i  validated in constructor
            throw new IllegalArgumentException("Name must not be blank");
        }
        Objects.requireNonNull(dateOfBirth, "Date of birth must not be null");
        if (dateOfBirth.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Date of birth cannot be in the future");
        }
        Objects.requireNonNull(hobbies, "Hobbies must not be null");

        this.name = name;
        this.dateOfBirth = dateOfBirth;                    // LocalDate is immutable: safe to keep
        this.hobbies = List.copyOf(hobbies);               // (4) defensive copy IN
    }

    public String getName()           { return name; }
    public LocalDate getDateOfBirth() { return dateOfBirth; } // for the private accesss modifier

    public List<String> getHobbies() {
        return hobbies;                                    // (5) already unmodifiable: safe OUT
    }

    // (6) No setters. To "change" a value, build a new object instead.
    public Immutableperson withName(String newName) {
        return new Immutableperson(newName, dateOfBirth, hobbies);
    }

    @Override
    public boolean equals(Object o) {                      // this is a  value-based equality
        if (this == o) return true;
        if (!(o instanceof Immutableperson)) return false;
        Immutableperson other = (Immutableperson) o;
        return name.equals(other.name)
                && dateOfBirth.equals(other.dateOfBirth)
                && hobbies.equals(other.hobbies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateOfBirth, hobbies);
    }

    @Override
    public String toString() {
        return "ImmutablePerson{name=" + name + ", born=" + dateOfBirth +
                ", hobbies=" + hobbies + "}";
    }
}
