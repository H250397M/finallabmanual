
public abstract class LibraryItem implements Borrowable {
    private final String id;
    private final String title;
    private boolean available = true;
    private Loan currentLoan;           // null when the item is on the shelf

    protected LibraryItem(String id, String title) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Item id must not be blank");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Item title must not be blank");
        }
        this.id = id.trim();
        this.title = title.trim();
    }

    /** Each subclass decides how long it may be kept (polymorphism). */
    public abstract int loanPeriodDays();

    /** Label used in menus and CSV files, e.g. "BOOK" or "DVD". */
    public abstract String typeName();

    public String getId()        { return id; }
    public String getTitle()     { return title; }
    public boolean isAvailable() { return available; }
    public Loan getCurrentLoan() { return currentLoan; }

    // package-private: only Library (same package) may restore state from disk
    void setAvailable(boolean available) { this.available = available; }
    void setCurrentLoan(Loan loan)       { this.currentLoan = loan; }

    @Override
    public void borrow(Member member) throws ItemNotAvailableException, MemberLimitExceededException {
        if (!available) {
            throw new ItemNotAvailableException("'" + title + "' is already on loan");
        }
        Loan loan = new Loan(this, member, java.time.LocalDate.now());
        member.addLoan(loan);           // throws if the member is at the limit
        currentLoan = loan;             // only change state once the loan succeeded
        available = false;
    }

    @Override
    public void giveBack() {
        if (available) {
            throw new IllegalStateException("'" + title + "' is not on loan");
        }
        currentLoan.getMember().removeLoan(currentLoan);
        currentLoan = null;
        available = true;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof LibraryItem other && id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %s (%d days) %s", typeName(), id, title,
                loanPeriodDays(), available ? "available" : "ON LOAN");
    }
}
