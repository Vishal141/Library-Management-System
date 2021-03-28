package sample.requests;

import sample.Members.Member;
import sample.Members.TempMember;

public class AddMemberRequest {
    private String apiKey;
    private TempMember member;

    public AddMemberRequest() {
    }

    public AddMemberRequest(String apiKey, TempMember member) {
        this.apiKey = apiKey;
        this.member = member;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public TempMember getMember() {
        return member;
    }

    public void setMember(TempMember member) {
        this.member = member;
    }
}
