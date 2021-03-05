package sample.Members;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import sample.Database.MemberDb;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditMember implements Initializable {
    @FXML
    JFXTextField mId,mName,mEmail,mContactNo;

    private MemberDb memberDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        memberDb = new MemberDb();
    }

    public void save(ActionEvent actionEvent) {
        String id = mId.getText();
        if (id==null)
            return;
        Member member = memberDb.getMemberById(id);
        if(member!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirm");
            alert.setContentText("Are sure want to edit details of member: "+member.getName());

            Optional<ButtonType> response = alert.showAndWait();

            if(response.get()==ButtonType.OK){
                if (mName!=null && !mName.getText().equals(""))
                    member.setName(mName.getText());
                if (mEmail!=null && !mEmail.getText().equals(""))
                    member.setEmailId(mEmail.getText());
                if(mContactNo!=null && !mContactNo.getText().equals(""))
                    member.setMobileNo(mContactNo.getText());

                TempMember member1 = new TempMember(member.getName(),member.getId(),member.getEmailId(),member.getMobileNo());
                if(memberDb.editMemberDetails(member1)){
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Successful");
                    alert1.setContentText("Member details are successfully updated");
                    alert1.showAndWait();
                    cancel(actionEvent);
                }else{
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Error");
                    alert1.setContentText("update operation is failed");
                    alert1.showAndWait();
                }
            }else{
                Alert alert1 = new Alert(Alert.AlertType.WARNING);
                alert1.setHeaderText(null);
                alert1.setTitle("Canceled");
                alert1.setContentText("update operation is canceled");
                alert1.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("No such member is exist");
            alert.showAndWait();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) mId.getScene().getWindow();
        stage.close();
    }

    public void inflateData(Member member) {
        mId.setText(member.getId());
        mName.setText(member.getName());
        mEmail.setText(member.getEmailId());
        mContactNo.setText(member.getMobileNo());
    }
}
