package sample.Members;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import sample.Controller;
import sample.Database.MemberDb;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MemberList implements Initializable {

    @FXML
    AnchorPane anchorPane;
    @FXML
    TableView<Member> tableView;
    @FXML
    TableColumn<Member,String> id,name,email,contactNO;

    private ObservableList<Member> list = FXCollections.observableArrayList();

    private MemberDb memberDb;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        memberDb = new MemberDb();
        list.addAll(memberDb.getMembers());
        tableView.getItems().setAll(list);
    }

    public void initCol(){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        email.setCellValueFactory(new PropertyValueFactory<>("emailId"));
        contactNO.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));
    }

    public static Parent getRoot() {
        Parent root=null;
        try {
            root = FXMLLoader.load(MemberList.class.getResource("MemberList.fxml"));
        }catch (Exception e){
            e.printStackTrace();
        }
        return root;
    }

    public void handleEditMember(ActionEvent actionEvent) {
        Member member = tableView.getSelectionModel().getSelectedItem();

        if (member==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Member is not selected.\nPlease select a Member.");
            alert.showAndWait();
            return;
        }

        Stage stage = (Stage)anchorPane.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditMember.fxml"));
            Parent root = loader.load();

            EditMember editMember = (EditMember)loader.getController();
            editMember.inflateData(member);

            stage.setTitle("Edit Member details");
            stage.setScene(new Scene(root,350,270));
            Controller.setStageIcon(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeleteMember(ActionEvent actionEvent) {
        //fetching the selected item
        Member member = tableView.getSelectionModel().getSelectedItem();
        if (member!=null){
            if (!memberDb.hasIssued(member.getId())){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to delete Member: "+member.getName());

                Optional<ButtonType> response = alert.showAndWait();
                if (response.get()==ButtonType.OK){
                    if (memberDb.deleteMember(member.getId())){
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Deleted");
                        alert.setHeaderText(null);
                        alert.setContentText("Member is deleted successfully");
                        alert.showAndWait();

                        list.clear();
                        list.addAll(memberDb.getMembers());
                        tableView.getItems().setAll(list);

                    }
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("Member has issued a book\nyou can't delete him right now.");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Member is not selected.\nPlease select a Member.");
            alert.showAndWait();
        }
    }
}
