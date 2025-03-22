import java.util.Scanner;
import java.util.List;

/**
 * Kütüphane Yönetim Sistemi'nin ana sınıfı.
 * Kullanıcı arayüzünü ve menü sistemini yönetir.
 * Kullanıcıdan alınan komutlara göre ilgili işlemleri gerçekleştirir.
 */
public class Main {
    static Scanner input = new Scanner(System.in);  // Kullanıcı girişi için Scanner nesnesi
    static Library library = new Library();         // Kütüphane nesnesi

    /**
     * Programın ana metodu
     * @param args Komut satırı argümanları
     */
    public static void main(String[] args) {
        initLibraryData();
        String isContinue = "y";
        while (isContinue.equalsIgnoreCase("y")) {
            showMenu();
            int selectedMenu = chooseMenu();
            if (selectedMenu == 1) {
                showBooks();
            } else if (selectedMenu == 2) {
                showMembers();
            } else if (selectedMenu == 3) {
                addMember();
            } else if (selectedMenu == 4) {
                borrowBook();
            } else if (selectedMenu == 5) {
                returnBook();
            } else if (selectedMenu == 6) {
                showOverdueBooks();
            } else {
                System.out.println("Hatalı seçim");
            }
            System.out.println("Devam etmek istiyor musunuz? (y/n)");
            isContinue = input.next();
        }
        input.close();
    }

    /**
     * Ana menüyü ekrana yazdırır
     */
    public static void showMenu() {
        System.out.println("================================");
        System.out.println("1. Kitap listesini göster");
        System.out.println("2. Üyeleri göster");
        System.out.println("3. Üye ekle");
        System.out.println("4. Kitap ödünç al");
        System.out.println("5. Kitap iade et");
        System.out.println("6. Geciken kitapları göster");
        System.out.println("================================");
    }

    /**
     * Kullanıcıdan menü seçimi alır
     * @return Seçilen menü numarası
     */
    public static int chooseMenu() {
        System.out.print("Seçiminiz: ");
        return input.nextInt();
    }

    /**
     * Kütüphaneye örnek kitaplar ekler
     */
    public static void initLibraryData() {
        book book1 = new book("1", "Ateşten Gömlek", "Halide Edip Adıvar", "9789750719381");
        library.addBook(book1);

        book book2 = new book("2", "Çizgili Pijamalı Çocuk", "John Boyne", "9789750719382");
        library.addBook(book2);

        book book3 = new book("3", "Suç ve Ceza", "Fyodor Dostoyevski", "9789750719383");
        library.addBook(book3);
    }

    /**
     * Kütüphanedeki tüm kitapları listeler
     */
    public static void showBooks() {
        List<book> books = library.getBooks();
        if (books.isEmpty()) {
            System.out.println("\nKütüphanede hiç kitap bulunmamaktadır.");
            return;
        }
        System.out.println("\nKitap Listesi:");
        for (book book : books) {
            System.out.println("ID: " + book.id + 
                             " - Başlık: " + book.title + 
                             " - Yazar: " + book.author +
                             " - ISBN: " + book.isbn +
                             " - Durum: " + (book.isAvailable ? "Müsait" : "Ödünç Alındı"));
            if (!book.isAvailable) {
                System.out.println("  Ödünç Alan: " + book.borrowedBy);
                System.out.println("  Ödünç Alma Tarihi: " + book.borrowDate);
                System.out.println("  İade Tarihi: " + book.returnDate);
            }
        }
    }

    /**
     * Kütüphaneye kayıtlı tüm üyeleri listeler
     */
    public static void showMembers() {
        List<Member> members = library.getMembers();
        if (members.isEmpty()) {
            System.out.println("\nKütüphanede hiç üye bulunmamaktadır.");
            return;
        }
        System.out.println("\nÜye Listesi:");
        for (Member member : members) {
            System.out.println("ID: " + member.id + 
                             " - İsim: " + member.name +
                             " - Telefon: " + member.phone +
                             " - E-posta: " + member.email);
            if (!member.borrowedBooks.isEmpty()) {
                System.out.println("Ödünç Alınan Kitaplar:");
                for (book book : member.borrowedBooks) {
                    System.out.println("  - " + book.title + " (İade: " + book.returnDate + ")");
                }
            }
        }
    }

    /**
     * Kütüphaneye yeni üye ekler
     */
    public static void addMember() {
        System.out.print("Üye ID: ");
        String id = input.next();
        
        if (library.isMemberIdExist(id)) {
            System.out.println("Bu ID'ye sahip bir üye zaten mevcut!");
            return;
        }
        
        System.out.print("Üye Adı: ");
        String name = input.next();
        
        System.out.print("Telefon: ");
        String phone = input.next();
        
        System.out.print("E-posta: ");
        String email = input.next();
        
        Member member = new Member(id, name, phone, email);
        library.addMember(member);
        System.out.println("Üye başarıyla eklendi!");
    }

    /**
     * Kitap ödünç alma işlemini gerçekleştirir
     */
    public static void borrowBook() {
        System.out.print("Üye ID: ");
        String memberId = input.next();
        
        if (!library.isMemberIdExist(memberId)) {
            System.out.println("Bu ID'ye sahip bir üye bulunamadı!");
            return;
        }
        
        System.out.print("Kitap ID: ");
        String bookId = input.next();
        
        library.giveBook(bookId, memberId);
        System.out.println("Kitap ödünç alma işlemi gerçekleştirildi!");
    }

    /**
     * Kitap iade işlemini gerçekleştirir
     */
    public static void returnBook() {
        System.out.print("Üye ID: ");
        String memberId = input.next();
        
        if (!library.isMemberIdExist(memberId)) {
            System.out.println("Bu ID'ye sahip bir üye bulunamadı!");
            return;
        }
        
        System.out.print("Kitap ID: ");
        String bookId = input.next();
        
        library.returnBook(bookId, memberId);
        System.out.println("Kitap iade işlemi gerçekleştirildi!");
    }

    /**
     * Geciken kitapları listeler
     */
    public static void showOverdueBooks() {
        List<book> overdueBooks = library.getOverdueBooks();
        if (overdueBooks.isEmpty()) {
            System.out.println("\nGeciken kitap bulunmamaktadır.");
            return;
        }
        System.out.println("\nGeciken Kitaplar:");
        for (book book : overdueBooks) {
            System.out.println("Başlık: " + book.title);
            System.out.println("Ödünç Alan: " + book.borrowedBy);
            System.out.println("İade Tarihi: " + book.returnDate);
            System.out.println("------------------------");
        }
    }
}