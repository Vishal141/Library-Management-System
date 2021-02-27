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
import javafx.stage.Stage;
import sample.Database.login_db;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Register implements Initializable {

    @FXML
    JFXTextField username,email,name;
    @FXML
    JFXPasswordField password;

    private Stage stage;

    private login_db loginDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginDb = new login_db();
    }

    public void signUp(ActionEvent actionEvent) {
        String uname = username.getText();
        String eml = email.getText();
        String Name = name.getText();
        String pass = password.getText();

        if (!(uname==null || uname.equals("") ||
                eml==null || eml.equals("") ||
                Name==null || Name.equals("")||
                pass==null || pass.equals("")))
        {
            if(loginDb.register(Name,uname,eml,pass)){
                stage = (Stage)username.getScene().getWindow();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/sample/dashboard/Dashboard.fxml"));
                    stage.setTitle("Library Assistance");
                    stage.setScene(new Scene(root,650,470));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Registration failed");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("All the fields are mandatory.");
            alert.showAndWait();
        }

    }

    public void backLogin(ActionEvent actionEvent) {
        stage = (Stage)username.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/login/Login.fxml"));
            stage.setTitle("Login");
            stage.setScene(new Scene(root,600,400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
