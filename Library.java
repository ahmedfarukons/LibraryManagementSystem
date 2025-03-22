import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Library {
    private List<book> books;
    private List<Member> members;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(book book) {
        books.add(book);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public List<book> getBooks() {
        return books;
    }

    public List<Member> getMembers() {
        return members;
    }

    public boolean isMemberIdExist(String id) {
        for (Member member : this.members) {
            if (member.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    public void giveBook(String bookId, String memberId) {
        book book = this.getBookById(bookId);
        Member member = this.getMemberById(memberId);
        
        if (book != null && member != null && book.isAvailable && member.canBorrow()) {
            this.books.remove(book);
            member.borrowedBooks.add(book);
            book.isAvailable = false;
            book.borrowedBy = memberId;
            book.borrowDate = LocalDate.now().format(dateFormatter);
            book.returnDate = LocalDate.now().plusDays(14).format(dateFormatter);
        }
    }

    public void returnBook(String bookId, String memberId) {
        Member member = this.getMemberById(memberId);
        if (member != null) {
            book bookToReturn = null;
            for (book book : member.borrowedBooks) {
                if (book.id.equals(bookId)) {
                    bookToReturn = book;
                    break;
                }
            }
            
            if (bookToReturn != null) {
                member.borrowedBooks.remove(bookToReturn);
                bookToReturn.isAvailable = true;
                bookToReturn.borrowedBy = null;
                bookToReturn.borrowDate = null;
                bookToReturn.returnDate = null;
                this.books.add(bookToReturn);
            }
        }
    }

    private Member getMemberById(String id) {
        for (Member member : this.members) {
            if (member.id.equals(id)) {
                return member;
            }
        }
        return null;
    }

    private book getBookById(String id) {
        for (book book : this.books) {
            if (book.id.equals(id)) {
                return book;
            }
        }
        return null;
    }

    public List<book> getOverdueBooks() {
        List<book> overdueBooks = new ArrayList<>();
        LocalDate today = LocalDate.now();
        
        for (Member member : members) {
            for (book book : member.borrowedBooks) {
                if (book.returnDate != null) {
                    LocalDate returnDate = LocalDate.parse(book.returnDate, dateFormatter);
                    if (today.isAfter(returnDate)) {
                        overdueBooks.add(book);
                    }
                }
            }
        }
        return overdueBooks;
    }
}

