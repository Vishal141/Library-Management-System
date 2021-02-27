package sample.settings;

public class Preferences {
    private static int NoOfDays=7;
    private static float FinePerDay=5;
    private static String USERNAME;
    private static String PASSWORD;

    public static int getNoOfDays() {
        return NoOfDays;
    }

    public void setNoOfDays(int noOfDays) {
        NoOfDays = noOfDays;
    }

    public float getFinePerDay() {
        return FinePerDay;
    }

    public void setFinePerDay(float finePerDay) {
        FinePerDay = finePerDay;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        Preferences.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        Preferences.PASSWORD = PASSWORD;
    }
}
