// imports in java like list array list

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

// creating class bacnkaccount
public class BankAccount {
    private final String accountNumber;     //
    private final String owner;             //
    private double balance;                 //
    private final List<String> history = new ArrayList<>();   // never exposed


    //using if and esle so as not to get invalid values
    // for owner bank account and the opening balance

    public BankAccount(String accountNumber, String owner, double openingBalance) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number must not be blank");
        }
        if (owner == null || owner.isBlank()) {
            throw new IllegalArgumentException("Owner must not be blank");
        }
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.owner = owner;
        this.balance = openingBalance;
        record("OPENED with " + money(openingBalance));
    }
// double maybe our amount will have ,,
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        balance += amount;
        record("DEPOSIT " + money(amount) + " -> balance " + money(balance));
    }

    // if a user add money eg at CBZ it is the money he/she have thus the balance+amount

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        if (amount > balance) {
            throw new IllegalStateException(
                    "Insufficient funds: balance " + money(balance) +
                            ", requested " + money(amount));
        }
        balance -= amount;
        record("WITHDRAW " + money(amount) + " -> balance " + money(balance));
    }
    // vice versa for deposit so we subrtacted here

    public double getBalance()       { return balance; } // no we get our methods
    public String getAccountNumber() { return accountNumber; }
    public String getOwner()         { return owner; }

    //  returns an UNMODIFIABLE COPY (a snapshot), never the real list.
    public List<String> getTransactionHistory() {
        return List.copyOf(history); // now for the imported list to work it is now shown here
    }

    private void record(String entry) {
        history.add(entry);
    }

    private static String money(double amount) {
        return String.format(Locale.US, "%.2f", amount); //%2f means two decimal numbers
    }
}


