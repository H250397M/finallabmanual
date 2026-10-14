
// Exercise 3 (intermediate): abstract Account with a concrete deposit() and an abstract monthlyFee()
abstract class Account {
    private final String owner;
    protected double balance;      // shared state: this is why Account is an abstract class and not an interface

    Account(String owner, double openingBalance) {
        this.owner = owner;
        this.balance = openingBalance;
    }

    // concrete: identical for every kind of account, so it is written once here
    void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit must be positive");
            return;
        }
        balance += amount;
    }

    // abstract: each account type charges differently
    abstract double monthlyFee();

    // concrete method that relies on the abstract one
    void applyMonthlyFee() {
        balance -= monthlyFee();
    }

    void show() {
        System.out.printf("%s (%s): balance = %.2f, fee = %.2f%n",
                owner, getClass().getSimpleName(), balance, monthlyFee());
    }
}

class SavingsAccount extends Account {
    SavingsAccount(String owner, double openingBalance) { super(owner, openingBalance); }

    // no fee when the balance is at least 1000, otherwise a small fee
    @Override double monthlyFee() { return balance >= 1000 ? 0.0 : 5.0; }
}

class CurrentAccount extends Account {
    CurrentAccount(String owner, double openingBalance) { super(owner, openingBalance); }

    @Override double monthlyFee() { return 15.0; }    // flat fee
}

public class AccountDemo {
    public static void main(String[] args) {
        Account[] accounts = {
                new SavingsAccount("Tendai", 500),
                new CurrentAccount("Chipo", 2000)
        };

        for (Account a : accounts) {
            a.deposit(250);        // inherited, same code for both
            a.show();
            a.applyMonthlyFee();
            a.show();
            System.out.println("---");
        }
    }
}