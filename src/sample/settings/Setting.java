package sample.settings;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.login_db;

import java.net.URL;
import java.util.ResourceBundle;

public class Setting implements Initializable {

    @FXML
    AnchorPane body;
    @FXML
    JFXTextField noOfDays,finePerDay,username;
    @FXML
    JFXPasswordField password;

    private Stage stage;

    private login_db loginDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loginDb = new login_db();
    }

    public void save(ActionEvent actionEvent) {
        String uname = username.getText();
        String noOfD = noOfDays.getText();
        String fine = finePerDay.getText();
        String pass = password.getText();

        if (!(uname==null || uname.equals("") ||
                noOfD==null || noOfD.equals("") ||
                fine==null || fine.equals("")||
                pass==null || pass.equals("")))
        {
            if (loginDb.editPreferences(Integer.parseInt(noOfD),Float.parseFloat(fine),uname,pass)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setHeaderText(null);
                alert.setContentText("settings are updated successfully");
                alert.showAndWait();
                cancel(actionEvent);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("settings update operation failed.");
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

    public void cancel(ActionEvent actionEvent) {
        stage = (Stage)body.getScene().getWindow();
        stage.close();
    }
}
