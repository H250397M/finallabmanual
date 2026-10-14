import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/** Console menu for the Library Management System. */
public class Main {
    private static final Path DATA_DIR = Path.of("data");

    public static void main(String[] args) {
        LibraryStorage storage = new LibraryStorage(DATA_DIR);
        Library library;
        try {
            library = storage.load();
            System.out.println("Loaded " + library.getItems().size() + " items, "
                    + library.getMembers().size() + " members.");
        } catch (IOException e) {
            System.out.println("Could not read saved data (" + e.getMessage() + "). Starting empty.");
            library = new Library();
        }

        Scanner in = new Scanner(System.in);
        boolean running = true;
        while (running) {
            printMenu();
            String choice = prompt(in, "Choose: ");
            if (choice == null) choice = "0";           // end of input behaves like Exit
            try {
                switch (choice.trim()) {
                    case "1" -> addItem(in, library);
                    case "2" -> registerMember(in, library);
                    case "3" -> borrow(in, library);
                    case "4" -> giveBack(in, library);
                    case "5" -> listOverdue(library);
                    case "6" -> search(in, library);
                    case "7" -> listAll(library);
                    case "0" -> running = false;
                    default -> System.out.println("Please choose a number from the menu.");
                }
            } catch (ItemNotAvailableException | MemberLimitExceededException e) {
                System.out.println("Cannot borrow: " + e.getMessage());
            } catch (IllegalArgumentException | IllegalStateException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        try {
            storage.save(library);
            System.out.println("Data saved to " + DATA_DIR.toAbsolutePath() + ". Goodbye.");
        } catch (IOException e) {
            System.out.println("WARNING: could not save data: " + e.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("""

                ===== LIBRARY MENU =====
                1. Add item (book / DVD)
                2. Register member
                3. Borrow item
                4. Return item
                5. List overdue items
                6. Search by title
                7. List all items and members
                0. Save and exit""");
    }

    private static String prompt(Scanner in, String text) {
        System.out.print(text);
        return in.hasNextLine() ? in.nextLine() : null;
    }

    /** Like prompt but never returns null (empty text on end of input). */
    private static String ask(Scanner in, String text) {
        String s = prompt(in, text);
        return s == null ? "" : s.trim();
    }

    private static void addItem(Scanner in, Library library) {
        String type = ask(in, "Type (book/dvd): ");
        String id = ask(in, "Item id: ");
        String title = ask(in, "Title: ");
        String extra = type.equalsIgnoreCase("dvd")
                ? ask(in, "Duration in minutes: ")
                : ask(in, "Author: ");
        LibraryItem item = ItemFactory.create(type, id, title, extra);
        library.addItem(item);
        System.out.println("Added: " + item);
    }

    private static void registerMember(Scanner in, Library library) {
        String id = ask(in, "Member id: ");
        String name = ask(in, "Name: ");
        Member m = new Member(id, name);
        library.registerMember(m);
        System.out.println("Registered: " + m);
    }

    private static void borrow(Scanner in, Library library)
            throws ItemNotAvailableException, MemberLimitExceededException {
        String memberId = ask(in, "Member id: ");
        String itemId = ask(in, "Item id: ");
        Loan loan = library.borrow(memberId, itemId);
        System.out.println("Borrowed. Due back on " + loan.getDueDate());
    }

    private static void giveBack(Scanner in, Library library) {
        String itemId = ask(in, "Item id: ");
        library.giveBack(itemId);
        System.out.println("Returned: " + library.findItem(itemId).getTitle());
    }

    private static void listOverdue(Library library) {
        LocalDate today = LocalDate.now();
        List<Loan> overdue = library.listOverdue(today);
        if (overdue.isEmpty()) {
            System.out.println("No overdue items.");
            return;
        }
        for (Loan loan : overdue) {
            System.out.printf("%s - borrowed by %s (%s), due %s, %d day(s) overdue%n",
                    loan.getItem().getTitle(), loan.getMember().getName(), loan.getMember().getId(),
                    loan.getDueDate(), loan.daysOverdue(today));
        }
    }

    private static void search(Scanner in, Library library) {
        String text = ask(in, "Title contains: ");
        List<LibraryItem> results = library.searchByTitle(text);
        if (results.isEmpty()) System.out.println("No matches.");
        else results.forEach(System.out::println);
    }

    private static void listAll(Library library) {
        System.out.println("-- Items --");
        library.getItems().forEach(System.out::println);
        System.out.println("-- Members --");
        library.getMembers().forEach(System.out::println);
    }
}
