package sample.requests;

public class SubmitRenewRequest {
    private String apiKey;
    private String bookId;
    private String type;

    public SubmitRenewRequest() {
    }

    public SubmitRenewRequest(String apiKey, String bookId,String type) {
        this.apiKey = apiKey;
        this.bookId = bookId;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
