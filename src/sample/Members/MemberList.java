package sample.Members;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import sample.Database.MemberDb;

import java.net.URL;
import java.util.ResourceBundle;

public class MemberList implements Initializable {

    @FXML
    AnchorPane anchorPane;
    @FXML
    TableView<Member> tableView;
    @FXML
    TableColumn<Member,String> id,name,email,contactNO;

    private ObservableList<Member> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCol();
        MemberDb memberDb = new MemberDb();
        list.addAll(memberDb.getMembers());
        tableView.setItems(list);
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
}
