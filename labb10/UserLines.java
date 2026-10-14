// FIRST EXERCISE 

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserLines {
    private static final int LINE_COUNT = 5;

    static void save(Path path, List<String> lines) throws IOException {
        Files.write(path, lines);                   // creates the file or overwrites it
    }

    static List<String> load(Path path) throws IOException {
        return Files.readAllLines(path);
    }

    public static void main(String[] args) {
        Path path = Path.of("lines.txt");           // relative: resolves against the WORKING directory
        List<String> typed = new ArrayList<>();

        // try-with-resources closes the Scanner automatically
        try (Scanner in = new Scanner(System.in)) {
            System.out.println("Type " + LINE_COUNT + " lines, pressing Enter after each:");
            while (typed.size() < LINE_COUNT) {
                System.out.print("Line " + (typed.size() + 1) + ": ");
                if (!in.hasNextLine()) {            // input ended early (Ctrl+D / Ctrl+Z, or piped input)
                    System.out.println();
                    System.out.println("Input ended after " + typed.size() + " line(s).");
                    break;
                }
                typed.add(in.nextLine());
            }
        }

        if (typed.isEmpty()) {
            System.out.println("Nothing to save.");
            return;
        }

        try {
            save(path, typed);
            System.out.println("Saved " + typed.size() + " line(s) to " + path.toAbsolutePath());

            List<String> readBack = load(path);
            System.out.println("Read back from the file:");
            for (int i = 0; i < readBack.size(); i++) {
                System.out.println("  " + (i + 1) + ": " + readBack.get(i));
            }
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());   // report it, never an empty catch
        }
    }
}


