public class book {
    public String id;
    public String title;
    public String author;
    public String isbn;
    public boolean isAvailable = true;
    public String borrowedBy = null;
    public String borrowDate = null;
    public String returnDate = null;

    public book(String id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}
