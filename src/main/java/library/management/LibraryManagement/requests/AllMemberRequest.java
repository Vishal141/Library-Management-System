package library.management.LibraryManagement.requests;


import java.util.ArrayList;
import library.management.LibraryManagement.Members.Member;

public class AllMemberRequest {
    private String apiKey;
    private ArrayList<Member> members;

    public AllMemberRequest() {
    }

    public AllMemberRequest(String apiKey, ArrayList<Member> members) {
        this.apiKey = apiKey;
        this.members = members;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<Member> members) {
        this.members = members;
    }
}
