// OUR 5 TH EXECISE


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class LearnerReport {

    // Loads every valid line. Bad lines are reported and skipped, not fatal.
    static List<Learner> load(Path file) throws IOException {
        List<Learner> result = new ArrayList<>();
        int lineNumber = 0;
        for (String line : Files.readAllLines(file)) {
            lineNumber++;
            if (line.isBlank()) continue;
            try {
                result.add(Learner.fromCsv(line));
            } catch (IllegalArgumentException e) {
                System.out.println("Skipping line " + lineNumber + ": " + e.getMessage());
            }
        }
        return result;
    }

    // One report file per band, e.g. report-A.txt
    static void writeReports(Map<String, List<Learner>> byBand) throws IOException {
        // A plain for loop, NOT forEach(lambda): Files.write throws a checked
        // IOException, and a lambda for Consumer cannot throw it.
        for (Map.Entry<String, List<Learner>> entry : byBand.entrySet()) {
            String band = entry.getKey();

            List<Learner> members = entry.getValue().stream()
                    .sorted(Comparator.comparingDouble(Learner::gpa).reversed()
                            .thenComparing(Learner::name))          // highest GPA first, ties by name
                    .toList();
            double average = members.stream().mapToDouble(Learner::gpa).average().orElse(0);

            List<String> lines = new ArrayList<>();
            lines.add("Band " + band + " report");
            lines.add("===============");
            lines.add(String.format(Locale.US, "Learners: %d, average GPA: %.2f", members.size(), average));
            lines.add("");
            for (Learner l : members) {
                lines.add(String.format(Locale.US, "%-8s roll=%-4d gpa=%.1f", l.name(), l.roll(), l.gpa()));
            }

            Path out = Path.of("report-" + band + ".txt");
            Files.write(out, lines);
            System.out.println("Wrote " + out + " (" + members.size() + " learners)");
        }
    }

    static void createSample(Path file) throws IOException {
        Files.write(file, List.of(
            "Tariro,101,3.8",
            "Farai,102,3.1",
            "Rudo,103,3.9",
            "Kuda,104,3.2",
            "Ben,105,2.7",
            "Cara,106,3.5"));
    }

    public static void main(String[] args) {
        Path csv = Path.of(args.length > 0 ? args[0] : "learners.csv");
        try {
            if (args.length == 0 && !Files.exists(csv)) {
                createSample(csv);
                System.out.println("No learners.csv found, so created a sample one.");
            }

            List<Learner> learners = load(csv);
            System.out.println("Loaded " + learners.size() + " learners from " + csv);

            // Group by band. TreeMap keeps the bands in order A, B, C.
            Map<String, List<Learner>> byBand = learners.stream()
                    .collect(Collectors.groupingBy(Learner::band, TreeMap::new, Collectors.toList()));

            writeReports(byBand);
        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O error: " + e.getMessage());
        }
    }
}


