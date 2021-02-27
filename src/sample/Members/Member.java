package sample.Members;

import javafx.beans.property.SimpleStringProperty;

public class Member {

    private final SimpleStringProperty name;
    private final SimpleStringProperty id;
    private final SimpleStringProperty emailId;
    private final SimpleStringProperty mobileNo;

    public Member(String name,String id,String emailId,String mobileNo){
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
        this.emailId = new SimpleStringProperty(emailId);
        this.mobileNo = new SimpleStringProperty(mobileNo);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getId() {
        return id.get();
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public String getEmailId() {
        return emailId.get();
    }

    public void setEmailId(String emailId) {
        this.emailId.set(emailId);
    }

    public String getMobileNo() {
        return mobileNo.get();
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo.set(mobileNo);
    }
}
