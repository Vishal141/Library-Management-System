package sample.requests;

import sample.Books.Book;
import sample.Books.TempBook;

import java.util.ArrayList;

public class AllBookRequest {
    private String apiKey;
    private ArrayList<TempBook> books;

    public AllBookRequest() {
    }

    public AllBookRequest(String apiKey, ArrayList<TempBook> books) {
        this.apiKey = apiKey;
        this.books = books;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ArrayList<TempBook> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<TempBook> books) {
        this.books = books;
    }
}
