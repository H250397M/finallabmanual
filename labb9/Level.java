public enum Level {
    LOW, MEDIUM, HIGH
}

/*
 * ===================== NOTES: Level =====================
 * Exercise 1 (Basic): the enum
 *
 *    - An enum is a class with a FIXED set of instances: LOW, MEDIUM and
 *      HIGH are the only Level objects that can ever exist.
 *    - It is type-safe: a method that takes a Level cannot be given an
 *      unrelated int or String such as 7 or "hgih".
 *    - Enum constants are compared with ==, which is correct for enums
 *      (it is wrong for String objects, where equals() is needed).
 *    - See LevelAdvice.java for the switch that uses it.
 * =========================================================
 */
