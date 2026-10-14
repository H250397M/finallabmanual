import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/** Immutable record of one item being borrowed by one member. */
public class Loan {
    private final LibraryItem item;
    private final Member member;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;

    /** New loan: the due date comes from the item's own loan period. */
    public Loan(LibraryItem item, Member member, LocalDate borrowDate) {
        this(item, member, borrowDate, borrowDate.plusDays(item.loanPeriodDays()));
    }

    /** Used when reloading a loan from file, so the stored due date is kept. */
    public Loan(LibraryItem item, Member member, LocalDate borrowDate, LocalDate dueDate) {
        if (item == null || member == null || borrowDate == null || dueDate == null) {
            throw new IllegalArgumentException("Loan fields must not be null");
        }
        if (dueDate.isBefore(borrowDate)) {
            throw new IllegalArgumentException("Due date cannot be before borrow date");
        }
        this.item = item;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public LibraryItem getItem()    { return item; }
    public Member getMember()       { return member; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate()   { return dueDate; }

    public boolean isOverdue(LocalDate today) {
        return today.isAfter(dueDate);
    }

    public long daysOverdue(LocalDate today) {
        return Math.max(0, ChronoUnit.DAYS.between(dueDate, today));
    }

    @Override
    public String toString() {
        return String.format("Loan{%s -> %s, borrowed %s, due %s}",
                item.getTitle(), member.getName(), borrowDate, dueDate);
    }
}
