/// for my challenge question

public class Temperature {
    private final double celsius;           // single canonical representation shown here

    // Private: outside code MUST use a factory method this is an accses modifier
    private Temperature(double celsius) {
        this.celsius = celsius;
    }

    public static Temperature fromCelsius(double c) {
        return new Temperature(c);
    }

    public static Temperature fromFahrenheit(double f) {
        return new Temperature((f - 32) * 5.0 / 9.0);
    }

    public double toCelsius()    { return celsius; }
    public double toFahrenheit() { return celsius * 9.0 / 5.0 + 32; }

    @Override
    public String toString() {
        return String.format("%.2f°C / %.2f°F", toCelsius(), toFahrenheit());
    }
}
/* Why static factories can be clearer than overloaded constructors
    1. Overloading cannot tell them apart. Both versions take one
      double, so two constructors Temperature(double) would not even
     compile. Constructors differ only by parameter types.
    2. Names explain meaning  fromFahrenheit(98.6) is clear, while
       new Temperature(98.6) does not say which unit is meant.
    3. A factory can validate, reject, or return a cached object. A
       constructor must always create a new object. */

