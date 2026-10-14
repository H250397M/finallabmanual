// Extending Exception creates a CHECKED exception (callers must handle or declare it)
class InvalidAgeException extends Exception {
    private final int invalidAge;

    public InvalidAgeException(String message, int age) {
        super(message);
        this.invalidAge = age;
    }

    public int getInvalidAge() {
        return invalidAge;
    }
}

public class InvalidAgeExceptionDemo {
    // 'throws' in signature warns callers that this method may throw a checked exception
    public static void validateAge(int age) throws InvalidAgeException {
        if (age < 0 || age > 120) {
            // EXPLAIN: 'throw' instantiates and throws the custom exception
            throw new InvalidAgeException("Age " + age + " is outside the valid range (0-120).", age);
        }
        System.out.println("Age " + age + " is valid.");
    }

    public static void main(String[] args) {
        int[] testAges = {25, -5, 135};

        for (int age : testAges) {
            try {
                validateAge(age);
            } catch (InvalidAgeException e) {
                System.out.println("Caught: " + e.getMessage() + " (Given age: " + e.getInvalidAge() + ")");
            }
        }
    }
}
