import java.util.Objects;

public final class Book {
    private final String isbn;              // identity of a book
    private final String title;

    public Book(String isbn, String title) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN must not be blank");
        }
        this.isbn = isbn;
        this.title = title;
    }

    public String getIsbn()  { return isbn; }
    public String getTitle() { return title; }

    // Parameter MUST be Object. equals(Book b) would only OVERLOAD it.
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                      // same reference
        if (!(o instanceof Book other)) return false;    // null or wrong type
        return isbn.equals(other.isbn);                   // equal if ISBN matches
    }

    // Uses the SAME field as equals(), so equal books get equal hash codes.
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Book{isbn=" + isbn + ", title=" + title + "}";
    }
}


