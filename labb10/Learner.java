// 5 EXE

public record Learner(String name, int roll, double gpa) {

    // Validation runs for every Learner, however it is created.
    public Learner {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name must not be blank");
        }
        if (gpa < 0 || gpa > 4.0) {
            throw new IllegalArgumentException("GPA must be between 0.0 and 4.0, but was " + gpa);
        }
    }

    public String toCsv() {
        return name + "," + roll + "," + gpa;
    }

    // Static factory: parses one CSV line back into a Learner.
    public static Learner fromCsv(String line) {
        String[] p = line.split(",");
        if (p.length != 3) {
            throw new IllegalArgumentException(
                "Expected 3 fields but found " + p.length + ": " + line);
        }
        return new Learner(p[0].trim(), Integer.parseInt(p[1].trim()), Double.parseDouble(p[2].trim()));
    }

    // Grade band calculated from the GPA, not stored.
    // A: 3.5 and above   B: 3.0 up to 3.5   C: below 3.0
    public String band() {
        if (gpa >= 3.5) return "A";
        if (gpa >= 3.0) return "B";
        return "C";
    }
}


