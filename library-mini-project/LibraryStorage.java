import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves and loads a Library as three CSV files (items, members, loans).
 * Single responsibility: this class knows about files, Library knows about rules.
 */
public class LibraryStorage {
    private static final String ITEMS = "items.csv";
    private static final String MEMBERS = "members.csv";
    private static final String LOANS = "loans.csv";

    private final Path dir;

    public LibraryStorage(Path dir) {
        this.dir = dir;
    }

    // ---------- SAVE ----------
    public void save(Library library) throws IOException {
        Files.createDirectories(dir);

        try (BufferedWriter w = Files.newBufferedWriter(dir.resolve(ITEMS), StandardCharsets.UTF_8)) {
            w.write("type,id,title,extra");
            w.newLine();
            for (LibraryItem item : library.getItems()) {
                String extra = (item instanceof Book b) ? b.getAuthor()
                        : String.valueOf(((Dvd) item).getDurationMinutes());
                w.write(csvLine(item.typeName(), item.getId(), item.getTitle(), extra));
                w.newLine();
            }
        }

        try (BufferedWriter w = Files.newBufferedWriter(dir.resolve(MEMBERS), StandardCharsets.UTF_8)) {
            w.write("id,name");
            w.newLine();
            for (Member m : library.getMembers()) {
                w.write(csvLine(m.getId(), m.getName()));
                w.newLine();
            }
        }

        try (BufferedWriter w = Files.newBufferedWriter(dir.resolve(LOANS), StandardCharsets.UTF_8)) {
            w.write("memberId,itemId,borrowDate,dueDate");
            w.newLine();
            for (Loan loan : library.allLoans()) {
                w.write(csvLine(loan.getMember().getId(), loan.getItem().getId(),
                        loan.getBorrowDate().toString(), loan.getDueDate().toString()));
                w.newLine();
            }
        }
    }

    // ---------- LOAD ----------
    /** Missing files simply mean "first run": an empty library is returned. */
    public Library load() throws IOException {
        Library library = new Library();

        for (String[] f : readRows(dir.resolve(ITEMS), 4)) {
            try {
                library.addItem(ItemFactory.create(f[0], f[1], f[2], f[3]));
            } catch (IllegalArgumentException e) {
                warn(ITEMS, f, e);
            }
        }
        for (String[] f : readRows(dir.resolve(MEMBERS), 2)) {
            try {
                library.registerMember(new Member(f[0], f[1]));
            } catch (IllegalArgumentException e) {
                warn(MEMBERS, f, e);
            }
        }
        for (String[] f : readRows(dir.resolve(LOANS), 4)) {
            try {
                library.restoreLoan(f[0], f[1], LocalDate.parse(f[2]), LocalDate.parse(f[3]));
            } catch (IllegalArgumentException | DateTimeParseException | MemberLimitExceededException e) {
                warn(LOANS, f, e);
            }
        }
        return library;
    }

    private static void warn(String file, String[] fields, Exception e) {
        System.err.println("Skipping bad row in " + file + " " + String.join("|", fields)
                + " (" + e.getMessage() + ")");
    }

    /** Reads a CSV, skips the header and blank/short rows. */
    private static List<String[]> readRows(Path file, int expectedColumns) throws IOException {
        List<String[]> rows = new ArrayList<>();
        if (!Files.exists(file)) return rows;
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line = r.readLine();                    // header
            while ((line = r.readLine()) != null) {
                if (line.isBlank()) continue;
                String[] fields = parseCsvLine(line);
                if (fields.length != expectedColumns) {
                    System.err.println("Skipping malformed row in " + file.getFileName() + ": " + line);
                    continue;
                }
                rows.add(fields);
            }
        }
        return rows;
    }

    // ---------- tiny CSV helpers (handle commas and quotes inside titles) ----------
    static String csvLine(String... fields) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(escape(fields[i]));
        }
        return sb.toString();
    }

    private static String escape(String s) {
        if (s.contains(",") || s.contains("\"") || s.contains("\n")) {
            return "\"" + s.replace("\"", "\"\"") + "\"";
        }
        return s;
    }

    static String[] parseCsvLine(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (inQuotes) {
                if (c == '"' && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    cur.append('"');
                    i++;
                } else if (c == '"') {
                    inQuotes = false;
                } else {
                    cur.append(c);
                }
            } else if (c == '"') {
                inQuotes = true;
            } else if (c == ',') {
                out.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(c);
            }
        }
        out.add(cur.toString());
        return out.toArray(new String[0]);
    }
}
