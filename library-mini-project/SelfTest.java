import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

/** Plain-Java checks (no JUnit needed). Run: java SelfTest */
public class SelfTest {
    private static int passed = 0;
    private static int failed = 0;

    private static void check(String name, boolean ok) {
        if (ok) { passed++; System.out.println("PASS  " + name); }
        else    { failed++; System.out.println("FAIL  " + name); }
    }

    public static void main(String[] args) throws Exception {
        Library lib = new Library();
        lib.addItem(new Book("B1", "Clean Code", "Robert Martin"));
        lib.addItem(new Book("B2", "Effective Java, 3rd Ed", "Joshua Bloch"));
        lib.addItem(new Book("B3", "Head First Design Patterns", "Freeman"));
        lib.addItem(new Book("B4", "The \"Quoted\" Title", "Anon"));
        lib.addItem(new Dvd("D1", "Java Masterclass", 120));
        lib.registerMember(new Member("M1", "Tendai"));
        lib.registerMember(new Member("M2", "Rudo"));

        // polymorphism
        check("Book loan period is 21", lib.findItem("B1").loanPeriodDays() == 21);
        check("Dvd loan period is 7", lib.findItem("D1").loanPeriodDays() == 7);

        // borrow + availability
        lib.borrow("M1", "B1");
        check("item unavailable after borrow", !lib.findItem("B1").isAvailable());
        try {
            lib.borrow("M2", "B1");
            check("double borrow rejected", false);
        } catch (ItemNotAvailableException e) {
            check("double borrow rejected (ItemNotAvailableException)", true);
        }

        // limit of 3
        lib.borrow("M1", "B2");
        lib.borrow("M1", "B3");
        try {
            lib.borrow("M1", "D1");
            check("4th loan rejected", false);
        } catch (MemberLimitExceededException e) {
            check("4th loan rejected (MemberLimitExceededException)", true);
        }
        check("failed 4th loan left item available", lib.findItem("D1").isAvailable());

        // unknown ids
        try { lib.borrow("NOPE", "B1"); check("unknown member rejected", false); }
        catch (IllegalArgumentException e) { check("unknown member rejected", true); }
        try { lib.borrow("M2", "NOPE"); check("unknown item rejected", false); }
        catch (IllegalArgumentException e) { check("unknown item rejected", true); }

        // validation
        try { new Member("", "x"); check("blank member id rejected", false); }
        catch (IllegalArgumentException e) { check("blank member id rejected", true); }
        try { new Dvd("D9", "Bad", 0); check("zero-minute DVD rejected", false); }
        catch (IllegalArgumentException e) { check("zero-minute DVD rejected", true); }

        // return
        lib.giveBack("B3");
        check("item available after return", lib.findItem("B3").isAvailable());
        check("member loan count drops", lib.findMember("M1").getLoans().size() == 2);
        try { lib.giveBack("B3"); check("double return rejected", false); }
        catch (IllegalStateException e) { check("double return rejected", true); }

        // streams: search
        List<LibraryItem> found = lib.searchByTitle("JAVA");
        check("search is case-insensitive and sorted (2 hits)",
                found.size() == 2 && found.get(0).getTitle().equals("Effective Java, 3rd Ed"));

        // overdue (look 30 days into the future: B1 and B2 are 21 days, so both overdue)
        LocalDate future = LocalDate.now().plusDays(30);
        check("2 overdue in 30 days", lib.listOverdue(future).size() == 2);
        check("none overdue today", lib.listOverdue(LocalDate.now()).isEmpty());

        // persistence round trip (commas and quotes in titles included)
        Path dir = Files.createTempDirectory("libtest");
        LibraryStorage storage = new LibraryStorage(dir);
        storage.save(lib);
        Library copy = storage.load();
        check("items reloaded", copy.getItems().size() == 5);
        check("members reloaded", copy.getMembers().size() == 2);
        check("comma title survives CSV", copy.findItem("B2").getTitle().equals("Effective Java, 3rd Ed"));
        check("quote title survives CSV", copy.findItem("B4").getTitle().equals("The \"Quoted\" Title"));
        check("loan state reloaded", !copy.findItem("B1").isAvailable()
                && copy.findMember("M1").getLoans().size() == 2);
        check("due date preserved", copy.findItem("B1").getCurrentLoan().getDueDate()
                .equals(lib.findItem("B1").getCurrentLoan().getDueDate()));
        check("reloaded item can be returned", returnsOk(copy, "B1"));

        // missing files = first run
        Library empty = new LibraryStorage(dir.resolve("nothing-here")).load();
        check("first run gives empty library", empty.getItems().isEmpty());

        System.out.println("\n" + passed + " passed, " + failed + " failed");
        if (failed > 0) System.exit(1);
    }

    private static boolean returnsOk(Library lib, String id) {
        try { lib.giveBack(id); return lib.findItem(id).isAvailable(); }
        catch (RuntimeException e) { return false; }
    }
}
