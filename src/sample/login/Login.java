package sample.login;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Controller;
import sample.Database.login_db;
import sample.dashboard.Dashboard;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    AnchorPane body;

    @FXML
    JFXTextField username;
    @FXML
    JFXPasswordField password;

    private Stage stage;

    private login_db loginDb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginDb = new login_db();
    }

    public void signIn(ActionEvent actionEvent) {
        String uname = username.getText();
        String pass = password.getText();
        if (!(uname==null || uname.equals("") || pass==null || pass.equals(""))){
            if (loginDb.login(uname,pass)){
                stage = (Stage)body.getScene().getWindow();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/dashboard/Dashboard.fxml"));
                    stage.setTitle("Library Assistant");
                    stage.setScene(new Scene(root,650,450));
                    Controller.setStageIcon(stage);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.setContentText("Login failed");
                alert.showAndWait();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("All fields are mandatory.");
            alert.showAndWait();
        }
    }

    public void signUp(ActionEvent actionEvent) {
        stage = (Stage) body.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/login/register.fxml"));
            stage.setTitle("Create an Account");
            stage.setScene(new Scene(root,600,400));
            Controller.setStageIcon(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Parent getRoot(){
        Parent root = null;
        try {
            root = FXMLLoader.load(Login.class.getResource("Login.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

}
