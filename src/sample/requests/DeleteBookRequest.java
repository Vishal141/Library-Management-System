package sample.requests;

public class DeleteBookRequest {
    private String apiKey;
    private String bookId;

    public DeleteBookRequest() {
    }

    public DeleteBookRequest(String apiKey, String bookId) {
        this.apiKey = apiKey;
        this.bookId = bookId;
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
}
