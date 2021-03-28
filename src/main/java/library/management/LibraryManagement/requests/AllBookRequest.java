package library.management.LibraryManagement.requests;

import java.util.ArrayList;
import library.management.LibraryManagement.Books.Book;

public class AllBookRequest {
    private String apiKey;
    private ArrayList<Book> books;

    public AllBookRequest() {
    }

    public AllBookRequest(String apiKey, ArrayList<Book> books) {
        this.apiKey = apiKey;
        this.books = books;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
