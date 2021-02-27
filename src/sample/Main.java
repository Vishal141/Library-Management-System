package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Database.Connect;
import sample.settings.Preferences;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Main extends Application {

    private Connect connect;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
      //  Parent root = AddBook.getParent();
        primaryStage.setTitle("Library Assistance");
        primaryStage.setScene(new Scene(root, 600, 600));
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
        Connection connection = connect.getConnection();
        String query = "SELECT * FROM preferences";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                Preferences preferences = new Preferences();
                preferences.setNoOfDays(rs.getInt("noOfDays"));
                preferences.setFinePerDay(rs.getFloat("finePerDay"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
