import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class Member {
    public String id;
    public String name;
    public String phone;
    public String email;
    public List<book> borrowedBooks;
    public int maxBorrowLimit = 3;

    public Member(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    public boolean canBorrow() {
        return borrowedBooks.size() < maxBorrowLimit;
    }
} 