package library.management.LibraryManagement.requests;

import library.management.LibraryManagement.Books.IssueBook;

public class IssueBookRequest {
    private String apiKey;
    private String bookId;
    private String memberId;
    private IssueBook book;

    public IssueBookRequest() {
    }

    public IssueBookRequest(String apiKey, String bookId, String memberId, IssueBook book) {
        this.apiKey = apiKey;
        this.bookId = bookId;
        this.memberId = memberId;
        this.book = book;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public IssueBook getBook() {
        return book;
    }

    public void setBook(IssueBook book) {
        this.book = book;
    }
}
