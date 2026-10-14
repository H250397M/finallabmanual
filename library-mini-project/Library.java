import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Core service: owns the collections and enforces the business rules. */
public class Library {
    private final Map<String, LibraryItem> items = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();

    // ---------- adding data ----------
    public void addItem(LibraryItem item) {
        if (items.containsKey(item.getId())) {
            throw new IllegalArgumentException("Item id already exists: " + item.getId());
        }
        items.put(item.getId(), item);
    }

    public void registerMember(Member member) {
        if (members.containsKey(member.getId())) {
            throw new IllegalArgumentException("Member id already exists: " + member.getId());
        }
        members.put(member.getId(), member);
    }

    // ---------- lookups ----------
    public LibraryItem findItem(String itemId) {
        LibraryItem item = items.get(itemId);
        if (item == null) throw new IllegalArgumentException("No such item: " + itemId);
        return item;
    }

    public Member findMember(String memberId) {
        Member m = members.get(memberId);
        if (m == null) throw new IllegalArgumentException("No such member: " + memberId);
        return m;
    }

    public List<LibraryItem> getItems() {
        return items.values().stream()
                .sorted(Comparator.comparing(LibraryItem::getId))
                .toList();
    }

    public List<Member> getMembers() {
        return members.values().stream()
                .sorted(Comparator.comparing(Member::getId))
                .toList();
    }

    // ---------- operations ----------
    public Loan borrow(String memberId, String itemId)
            throws ItemNotAvailableException, MemberLimitExceededException {
        Member member = findMember(memberId);      // 1. look up (IllegalArgumentException if missing)
        LibraryItem item = findItem(itemId);
        item.borrow(member);                       // 2 + 3. availability, limit, create loan
        return item.getCurrentLoan();
    }

    public void giveBack(String itemId) {
        findItem(itemId).giveBack();
    }

    public List<Loan> listOverdue(LocalDate today) {
        return members.values().stream()
                .flatMap(m -> m.getLoans().stream())
                .filter(loan -> loan.isOverdue(today))
                .sorted(Comparator.comparing(Loan::getDueDate))
                .toList();
    }

    public List<LibraryItem> searchByTitle(String text) {
        String needle = text.toLowerCase();
        return items.values().stream()
                .filter(i -> i.getTitle().toLowerCase().contains(needle))
                .sorted(Comparator.comparing(LibraryItem::getTitle))
                .toList();
    }

    public List<Loan> allLoans() {
        List<Loan> all = new ArrayList<>();
        for (Member m : members.values()) all.addAll(m.getLoans());
        return all;
    }

    /** Re-creates a stored loan while loading from disk (bypasses the 'today' due-date rule). */
    void restoreLoan(String memberId, String itemId, LocalDate borrowDate, LocalDate dueDate)
            throws MemberLimitExceededException {
        Member member = findMember(memberId);
        LibraryItem item = findItem(itemId);
        Loan loan = new Loan(item, member, borrowDate, dueDate);
        member.addLoan(loan);
        item.setCurrentLoan(loan);
        item.setAvailable(false);
    }
}
