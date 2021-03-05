package sample.Members;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Database.MemberDb;

import java.net.URL;
import java.util.ResourceBundle;

public class AddMember implements Initializable {

    @FXML
    AnchorPane body;
    @FXML
    JFXTextField name,memberId,emailId,mobileNo;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void addMember(ActionEvent actionEvent) {
        String Name = name.getText();
        String MemberId = memberId.getText();
        String email = emailId.getText();
        String mobile = mobileNo.getText();

        if(!(Name==null || Name.equals("") ||
             MemberId==null || MemberId.equals("") ||
                email==null || email.equals("") ||
                mobile==null || mobile.equals("")))
        {
            TempMember member = new TempMember(Name,MemberId,email,mobile);
            MemberDb memberDb = new MemberDb();
            memberDb.addMember(member);   //adding a new member in the library.

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Member is added successfully.");
            alert.show();

            cancel(actionEvent);  //closing the stage.

        }else{
            warningAlert();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        stage = (Stage) body.getScene().getWindow();
        stage.close();
    }

    public void warningAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("all fields are mandatory.");
        alert.show();
    }

    public static Parent getRoot(){
        Parent root=null;
        try {
            root = FXMLLoader.load(AddMember.class.getResource("AddMember.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return root;
    }

}
