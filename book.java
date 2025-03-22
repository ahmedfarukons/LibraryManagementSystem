/**
 * Kitap sınıfı, kütüphanedeki kitapların özelliklerini ve durumlarını tutar.
 * Her kitabın benzersiz bir ID'si, başlığı, yazarı ve ISBN numarası vardır.
 * Ayrıca kitabın ödünç alma durumu ve ilgili tarihler de takip edilir.
 */
public class book {
    public String id;          // Kitabın benzersiz kimlik numarası
    public String title;       // Kitabın başlığı
    public String author;      // Kitabın yazarı
    public String isbn;        // Kitabın ISBN numarası
    public boolean isAvailable = true;  // Kitabın ödünç alınabilir durumu
    public String borrowedBy = null;    // Kitabı ödünç alan üyenin ID'si
    public String borrowDate = null;    // Kitabın ödünç alınma tarihi
    public String returnDate = null;    // Kitabın iade edilmesi gereken tarih

    /**
     * Yeni bir kitap oluşturur
     * @param id Kitabın benzersiz kimlik numarası
     * @param title Kitabın başlığı
     * @param author Kitabın yazarı
     * @param isbn Kitabın ISBN numarası
     */
    public book(String id, String title, String author, String isbn) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }
}
