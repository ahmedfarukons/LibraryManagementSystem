import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Kütüphane sınıfı, kütüphanedeki tüm kitapları ve üyeleri yönetir.
 * Kitap ödünç alma, iade etme ve geciken kitapları takip etme gibi
 * temel kütüphane işlemlerini gerçekleştirir.
 */
public class Library {
    private List<book> books;           // Kütüphanedeki tüm kitapların listesi
    private List<Member> members;       // Kütüphaneye kayıtlı tüm üyelerin listesi
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // Tarih formatı

    /**
     * Yeni bir kütüphane oluşturur
     */
    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    /**
     * Kütüphaneye yeni bir kitap ekler
     * @param book Eklenecek kitap
     */
    public void addBook(book book) {
        books.add(book);
    }

    /**
     * Kütüphaneye yeni bir üye ekler
     * @param member Eklenecek üye
     */
    public void addMember(Member member) {
        members.add(member);
    }

    /**
     * Kütüphanedeki tüm kitapların listesini döndürür
     * @return Kitap listesi
     */
    public List<book> getBooks() {
        return books;
    }

    /**
     * Kütüphaneye kayıtlı tüm üyelerin listesini döndürür
     * @return Üye listesi
     */
    public List<Member> getMembers() {
        return members;
    }

    /**
     * Verilen ID'ye sahip bir üyenin var olup olmadığını kontrol eder
     * @param id Kontrol edilecek üye ID'si
     * @return Üye varsa true, yoksa false
     */
    public boolean isMemberIdExist(String id) {
        for (Member member : this.members) {
            if (member.id.equals(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Bir üyeye kitap ödünç verir
     * @param bookId Ödünç verilecek kitabın ID'si
     * @param memberId Kitabı ödünç alacak üyenin ID'si
     */
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

    /**
     * Bir üyeden kitap iadesi alır
     * @param bookId İade edilecek kitabın ID'si
     * @param memberId Kitabı iade edecek üyenin ID'si
     */
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

    /**
     * ID'ye göre üye bulur
     * @param id Aranacak üye ID'si
     * @return Bulunan üye veya null
     */
    private Member getMemberById(String id) {
        for (Member member : this.members) {
            if (member.id.equals(id)) {
                return member;
            }
        }
        return null;
    }

    /**
     * ID'ye göre kitap bulur
     * @param id Aranacak kitap ID'si
     * @return Bulunan kitap veya null
     */
    private book getBookById(String id) {
        for (book book : this.books) {
            if (book.id.equals(id)) {
                return book;
            }
        }
        return null;
    }

    /**
     * İade tarihi geçmiş kitapların listesini döndürür
     * @return Geciken kitapların listesi
     */
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

