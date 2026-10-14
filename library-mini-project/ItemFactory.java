
public class ItemFactory {
    private ItemFactory() { }

    /**
     * @param extra the author for a BOOK, or the duration in minutes for a DVD
     */
    public static LibraryItem create(String type, String id, String title, String extra) {
        return switch (type.trim().toUpperCase()) {
            case "BOOK" -> new Book(id, title, extra);
            case "DVD" -> {
                try {
                    yield new Dvd(id, title, Integer.parseInt(extra.trim()));
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("DVD duration must be a whole number: " + extra);
                }
            }
            default -> throw new IllegalArgumentException("Unknown item type: " + type);
        };
    }
}
