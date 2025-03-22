import java.util.ArrayList;
public class members {
    public String id;
    public String name;
    public ArrayList<book> barrowedBooks=new ArrayList<book>();
    public void receiveBook(book book){
        this.barrowedBooks.add(book);
    }
    public void giveBook(book book){
        this.barrowedBooks.remove(book);
    }
}
