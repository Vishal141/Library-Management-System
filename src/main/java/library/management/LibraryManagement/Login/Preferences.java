package library.management.LibraryManagement.Login;

public class Preferences {
    private String noOfDayWithoutFine;
    private String finePerDay;
    private String username;
    private String password;

    public Preferences(String noOfDayWithoutFine, String finePerDay, String username, String password) {
        this.noOfDayWithoutFine = noOfDayWithoutFine;
        this.finePerDay = finePerDay;
        this.username = username;
        this.password = password;
    }

    public String getNoOfDayWithoutFine() {
        return noOfDayWithoutFine;
    }

    public void setNoOfDayWithoutFine(String noOfDayWithoutFine) {
        this.noOfDayWithoutFine = noOfDayWithoutFine;
    }

    public String getFinePerDay() {
        return finePerDay;
    }

    public void setFinePerDay(String finePerDay) {
        this.finePerDay = finePerDay;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
