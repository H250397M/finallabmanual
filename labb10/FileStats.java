import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

public class FileStats {

    record Stats(int lines, int words, int characters) {}

    // Reads the file one line at a time, so even a huge file uses little memory.
    static Stats analyse(Path path) throws IOException {
        int lines = 0;
        int words = 0;
        int characters = 0;

        try (BufferedReader reader = Files.newBufferedReader(path)) {   // closed automatically
            String line;
            while ((line = reader.readLine()) != null) {
                lines++;
                characters += line.length();                // line breaks are not included
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {                   // "".split(...) would give 1 empty "word"
                    words += trimmed.split("\\s+").length;  // one or more whitespace characters
                }
            }
        }
        return new Stats(lines, words, characters);
    }

    public static void main(String[] args) {
        Path path;
        if (args.length > 0) {
            path = Path.of(args[0]);                        // java FileStats myfile.txt
        } else {
            path = Path.of("sample.txt");
            try {
                Files.write(path, List.of(
                    "Java makes file handling simple.",
                    "Streams and lambdas follow.",
                    "",
                    "  Indented line with   extra spaces  "));
            } catch (IOException e) {
                System.out.println("Could not create the sample file: " + e.getMessage());
                return;
            }
            System.out.println("No file given, so created " + path + " to analyse.");
        }

        try {
            Stats s = analyse(path);
            System.out.println("File      : " + path);
            System.out.println("Lines     : " + s.lines());
            System.out.println("Words     : " + s.words());
            System.out.println("Characters: " + s.characters() + " (not counting line breaks)");
        } catch (NoSuchFileException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not read the file: " + e.getMessage());
        }
    }
}

