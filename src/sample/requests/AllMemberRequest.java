package sample.requests;

import sample.Members.Member;
import sample.Members.TempMember;

import java.util.ArrayList;

public class AllMemberRequest {
    private String apiKey;
    private ArrayList<TempMember> members;

    public AllMemberRequest() {
    }

    public AllMemberRequest(String apiKey, ArrayList<TempMember> members) {
        this.apiKey = apiKey;
        this.members = members;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ArrayList<TempMember> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<TempMember> members) {
        this.members = members;
    }
}
