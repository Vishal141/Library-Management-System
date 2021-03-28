package library.management.LibraryManagement.requests;

import library.management.LibraryManagement.Members.Member;

public class AddMemberRequest {
    private String apiKey;
    private Member member;

    public AddMemberRequest() {
    }

    public AddMemberRequest(String apiKey, Member member) {
        this.apiKey = apiKey;
        this.member = member;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
