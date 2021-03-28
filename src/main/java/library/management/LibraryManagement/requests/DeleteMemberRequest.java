package library.management.LibraryManagement.requests;

public class DeleteMemberRequest {
    private String apiKey;
    private String memberId;

    public DeleteMemberRequest() {
    }

    public DeleteMemberRequest(String apiKey, String memberId) {
        this.apiKey = apiKey;
        this.memberId = memberId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
