package library.management.LibraryManagement.requests;

import library.management.LibraryManagement.Books.Book;

public class AddBookRequest {
    private String apiKey;
    private Book book;

    public AddBookRequest() {
    }

    public AddBookRequest(String apiKey, Book book) {
        this.apiKey = apiKey;
        this.book = book;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}

