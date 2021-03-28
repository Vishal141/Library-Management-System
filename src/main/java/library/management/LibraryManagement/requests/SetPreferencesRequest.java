package library.management.LibraryManagement.requests;

import library.management.LibraryManagement.Login.Preferences;

public class SetPreferencesRequest {
    private String apiKey;
    private Preferences preferences;

    public SetPreferencesRequest() {
    }

    public SetPreferencesRequest(String apiKey, Preferences preferences) {
        this.apiKey = apiKey;
        this.preferences = preferences;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void setPreferences(Preferences preferences) {
        this.preferences = preferences;
    }
}
