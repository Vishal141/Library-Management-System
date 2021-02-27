package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.login.Login;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    Text welcomeText;

    private static final String IMG_URL = "/resources/main_icon_library_management.jpg";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Stage stage = (Stage) welcomeText.getScene().getWindow();
                Parent root = Login.getRoot();
                Scene scene = new Scene(root,600,400);
                stage.setScene(scene);
                stage.setTitle("Login");
                stage.show();
            }
        });
    }

    public static void setStageIcon(Stage stage){
        stage.getIcons().add(new Image(IMG_URL));
    }
}
