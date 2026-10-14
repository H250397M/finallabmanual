public class LevelAdvice {

    // Switch EXPRESSION: it produces a value, and each case uses ->
    // (no fall-through, so no "break" is needed).
    public static String adviceFor(Level level) {
        return switch (level) {
            case LOW    -> "Low: safe to proceed, but keep an eye on it.";
            case MEDIUM -> "Medium: take care and double-check your work.";
            case HIGH   -> "High: stop and act now before it gets worse.";
        };                                    // no 'default' on purpose, see notes
    }

    // The same logic as a classic switch STATEMENT, to compare the styles.
    public static void printAdvice(Level level) {
        switch (level) {
            case LOW:
                System.out.println("LOW    -> " + adviceFor(level));
                break;
            case MEDIUM:
                System.out.println("MEDIUM -> " + adviceFor(level));
                break;
            case HIGH:
                System.out.println("HIGH   -> " + adviceFor(level));
                break;
        }
    }
}

/*
 * ===================== NOTES: LevelAdvice =====================
 * Exercise 1 (Basic): a switch that prints advice for each Level
 *
 * Why there is no 'default' in the switch expression
 *    - A switch expression over an enum must cover EVERY constant, and
 *      the compiler checks this. If you later add CRITICAL to Level,
 *      this method stops compiling until you handle it, so you cannot
 *      forget a case. A 'default' would hide that safety check.
 *
 * Switch expression vs the classic switch statement
 *    - The statement needs a 'break' after each case. Forgetting one
 *      causes fall-through and runs the next case as well.
 *    - The arrow form (->) never falls through and returns a value.
 *
 * Common mistake avoided: in a switch over an enum you write the bare
 * constant name (case LOW), not Level.LOW.
 * ==============================================================
 */
