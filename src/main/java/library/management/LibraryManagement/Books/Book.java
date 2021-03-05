package library.management.LibraryManagement.Books;

public class Book {

    private final String id;
    private final String title;
    private final String author;
    private final String publisher;
    private boolean isAvailable;    

    public Book(String id, String title, String author, String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isAvailable = true;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }
    
    public void setIsAvailable(boolean tf){
        this.isAvailable = tf;
    }

}
