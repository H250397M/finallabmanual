import java.util.LinkedHashMap;
import java.util.Map;

public enum Coin {
    PENNY(1), NICKEL(5), DIME(10), QUARTER(25);

    private final int cents;

    Coin(int cents) {                       // enum constructors are always private
        this.cents = cents;
    }

    public int getCents() {
        return cents;
    }

    // Returns how many of each coin make up 'amount' cents, using the
    // fewest coins. Largest coins first. Coins that are not used are left out.
    public static Map<Coin, Integer> fewestCoins(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative: " + amount);
        }
        Map<Coin, Integer> result = new LinkedHashMap<>();   // keeps insertion order
        int remaining = amount;
        Coin[] all = values();                               // PENNY, NICKEL, DIME, QUARTER
        for (int i = all.length - 1; i >= 0; i--) {          // walk from QUARTER down
            int count = remaining / all[i].cents;
            if (count > 0) {
                result.put(all[i], count);
                remaining -= count * all[i].cents;
            }
        }
        return result;
    }

    // Total number of coins in a result map.
    public static int totalCoins(Map<Coin, Integer> change) {
        int total = 0;
        for (int count : change.values()) {
            total += count;
        }
        return total;
    }
}

