package sample.requests;

import sample.Books.Book;
import sample.Books.TempBook;

public class AddBookRequest {
    private String apiKey;
    private TempBook book;

    public AddBookRequest() {
    }

    public AddBookRequest(String apiKey, TempBook book) {
        this.apiKey = apiKey;
        this.book = book;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public TempBook getBook() {
        return book;
    }

    public void setBook(TempBook book) {
        this.book = book;
    }
}

