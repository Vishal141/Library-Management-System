package library.management.LibraryManagement.Members;

public class Member {
    private String name;
    private String id;
    private String emailId;
    private String mobileNo;

    public Member(String name, String id, String emailId, String mobileNo) {
        this.name = name;
        this.id = id;
        this.emailId = emailId;
        this.mobileNo = mobileNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    
    
    
}
