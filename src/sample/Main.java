package sample;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Database.Connect;
import sample.requests.SetPreferencesRequest;
import sample.settings.Preferences;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Main extends Application {

    private Connect connect;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/login/Login.fxml"));
        primaryStage.setTitle("Library Assistance Login");
        primaryStage.setScene(new Scene(root, 600, 400));
        Controller.setStageIcon(primaryStage);
        primaryStage.show();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                connect = Connect.getInstance();
                setPreferences();
            }
        });

    }

    public void setPreferences(){
        try {
            Gson gson = new Gson();
            URL url = new URL("http://localhost:8080/preferences/get");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type","application/json; charset=UTF-8");

            String apiKey = "library_assistance";
            SetPreferencesRequest request = new SetPreferencesRequest(apiKey,null);

            String json1 = gson.toJson(request);

            httpURLConnection.setFixedLengthStreamingMode(json1.length());
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(json1.getBytes());
            os.flush();
            os.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String json = reader.readLine();

            request = gson.fromJson(json,SetPreferencesRequest.class);

            Preferences preferences = request.getPreferences();
            if(preferences!=null){
                Preferences.NoOfDayWithoutFine = preferences.getNoOfDayWithoutFine();
                Preferences.FinePerDay = preferences.getFinePerDay();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
