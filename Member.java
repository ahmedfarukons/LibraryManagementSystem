import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Üye sınıfı, kütüphane üyelerinin bilgilerini ve ödünç aldıkları kitapları tutar.
 * Her üyenin benzersiz bir ID'si, adı, telefon numarası ve e-posta adresi vardır.
 * Üyeler en fazla 3 kitap ödünç alabilir.
 */
public class Member {
    public String id;          // Üyenin benzersiz kimlik numarası
    public String name;        // Üyenin adı
    public String phone;       // Üyenin telefon numarası
    public String email;       // Üyenin e-posta adresi
    public List<book> borrowedBooks;  // Üyenin ödünç aldığı kitapların listesi
    public int maxBorrowLimit = 3;    // Üyenin ödünç alabileceği maksimum kitap sayısı

    /**
     * Yeni bir üye oluşturur
     * @param id Üyenin benzersiz kimlik numarası
     * @param name Üyenin adı
     * @param phone Üyenin telefon numarası
     * @param email Üyenin e-posta adresi
     */
    public Member(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    /**
     * Üyenin yeni kitap ödünç alıp alamayacağını kontrol eder
     * @return Üye limit aşımına ulaşmamışsa true, aşmışsa false
     */
    public boolean canBorrow() {
        return borrowedBooks.size() < maxBorrowLimit;
    }
} 