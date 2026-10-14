// --- APPROACH 1: NON-POLYMORPHIC (using instanceof checks) ---
class RawPayment {
    double amount;
    RawPayment(double amount) { this.amount = amount; }
}

class CardPayment extends RawPayment {
    String cardNumber;
    CardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }
}

class MobileMoneyPayment extends RawPayment {
    String phoneNumber;
    MobileMoneyPayment(double amount, String phoneNumber) {
        super(amount);
        this.phoneNumber = phoneNumber;
    }
}

class HardcodedProcessor {
    static void pay(RawPayment p) {
        // EXPLAIN: Fragile design — requires adding new 'if' checks whenever a new payment type is added
        if (p instanceof CardPayment card) {
            System.out.println("Processing Card " + card.cardNumber + " for $" + card.amount);
        } else if (p instanceof MobileMoneyPayment mobile) {
            System.out.println("Processing Mobile " + mobile.phoneNumber + " for $" + mobile.amount);
        }
    }
}

// --- APPROACH 2: POLYMORPHIC DESIGN (Clean & Scalable) ---
abstract class Payment {
    protected double amount;
    public Payment(double amount) { this.amount = amount; }

    // EXPLAIN: Each subtype implements its own processing logic
    public abstract void process();
}

class CleanCardPayment extends Payment {
    private String cardNumber;
    public CleanCardPayment(double amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    @Override
    public void process() {
        System.out.println("[Clean] Charged Card " + cardNumber + " for $" + amount);
    }
}

class CleanMobilePayment extends Payment {
    private String phoneNumber;
    public CleanMobilePayment(double amount, String phoneNumber) {
        super(amount);
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void process() {
        System.out.println("[Clean] Sent Mobile Request to " + phoneNumber + " for $" + amount);
    }
}

public class PaymentProcessorDemo {
    public static void main(String[] args) {
        System.out.println("=== Approach 1: Pattern Matching ===");
        HardcodedProcessor.pay(new CardPayment(100.0, "4111-XXXX-XXXX-1111"));
        HardcodedProcessor.pay(new MobileMoneyPayment(50.0, "+263771234567"));

        System.out.println("%n=== Approach 2: Polymorphic dispatch ===");
        Payment[] payments = {
                new CleanCardPayment(100.0, "4111-XXXX-XXXX-1111"),
                new CleanMobilePayment(50.0, "+263771234567")
        };

        for (Payment p : payments) {
            p.process(); // Clean dynamic dispatch
        }
    }
}