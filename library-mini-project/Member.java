import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Member {
    public static final int MAX_LOANS = 3;

    private final String id;
    private String name;
    private final List<Loan> loans = new ArrayList<>();

    public Member(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Member id must not be blank");
        }
        this.id = id.trim();
        setName(name);
    }

    public String getId()   { return id; }
    public String getName() { return name; }

    public final void setName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Member name must not be blank");
        }
        this.name = name.trim();
    }

    /** Read-only view, so outsiders cannot bypass the loan limit. */
    public List<Loan> getLoans() { return Collections.unmodifiableList(loans); }

    void addLoan(Loan loan) throws MemberLimitExceededException {
        if (loans.size() >= MAX_LOANS) {
            throw new MemberLimitExceededException(
                    name + " already has " + MAX_LOANS + " loans (the maximum)");
        }
        loans.add(loan);
    }

    void removeLoan(Loan loan) {
        loans.remove(loan);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Member other && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("Member{%s, %s, %d/%d loans}", id, name, loans.size(), MAX_LOANS);
    }
}
