class InsufficientFundsException extends Exception {
    private final double shortfall;

    public InsufficientFundsException(String message, double shortfall) {
        super(message);
        this.shortfall = shortfall;
    }

    public double getShortfall() {
        return shortfall;
    }
}

public class BankAccountWithExceptions {
    private final String accountNumber;
    private double balance;

    public BankAccountWithExceptions(String accountNumber, double balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    // EXPLAIN: Throws an exception on failure instead of returning a boolean flag
    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
        if (amount > balance) {
            double shortfall = amount - balance;
            throw new InsufficientFundsException(
                    "Insufficient funds in " + accountNumber + ". Balance: $" + balance, shortfall);
        }
        balance -= amount;
        System.out.printf("Successfully withdrew $%.2f. New balance: $%.2f%n", amount, balance);
    }

    public static void main(String[] args) {
        BankAccountWithExceptions acc = new BankAccountWithExceptions("ACC-301", 100.0);

        try {
            acc.withdraw(40.0); // Succeeds
            acc.withdraw(80.0); // Throws InsufficientFundsException
        } catch (InsufficientFundsException e) {
            System.out.println("Transaction Failed: " + e.getMessage());
            System.out.printf("Shortfall: $%.2f%n", e.getShortfall());
        }
    }
}

/*
 * EXPLAIN: Trade-offs of Exceptions vs Boolean Return Flags:
 * 1. Returning boolean (return false):
 *    - Advantage: Cheaper performance-wise; easy control flow.
 *    - Disadvantage: Callers can silently ignore the return value and assume withdrawal succeeded.
 *
 * 2. Throwing InsufficientFundsException:
 *    - Advantage: Forces callers to explicitly handle failure; carries contextual data (e.g., shortfall amount).
 *    - Disadvantage: Exception instantiation carries stack trace overhead; adds boilerplate try/catch blocks.
 */
