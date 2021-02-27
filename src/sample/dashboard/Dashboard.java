package sample.dashboard;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.effects.JFXDepthManager;
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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Books.AddBook;
import sample.Books.Book;
import sample.Books.BookList;
import sample.Books.IssueBook;
import sample.Database.MemberDb;
import sample.Database.book_db;
import sample.Members.AddMember;
import sample.Members.Member;
import sample.Members.MemberList;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {

    @FXML
    StackPane stackPane;
    @FXML
    HBox bookBox,memberBox;

    @FXML
    TextField bookIdBox,memberIdBox;
    @FXML
    Text BookTitle,BookAuthor,BookStatus,MemberName,contactNoBox;
    @FXML
    JFXTextField renewBookId;
    @FXML
    ListView<String> bookList;

    //private Stage stage;

    private book_db bookDb;
    private MemberDb memberDb;
    private IssueBook issueBook;

    private ObservableList<String> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        JFXDepthManager.setDepth(bookBox,1);
        JFXDepthManager.setDepth(memberBox,1);

        bookDb = new book_db();
        memberDb = new MemberDb();

        inVisibleText();

    }

    public void goToAddBook(ActionEvent actionEvent) throws Exception{
        loadWindow("/sample/Books/add_book.fxml","Add Book",300,350);
    }

    public void goToAddMember(ActionEvent actionEvent) {
        loadWindow("/sample/Members/AddMember.fxml","Add Member",260,380);
    }

    public void viewBooks(ActionEvent actionEvent) {
        loadWindow("/sample/Books/BookList.fxml","All Books",450,650);
    }

    public void viewMembers(ActionEvent actionEvent) {
        loadWindow("/sample/Members/MemberList.fxml","All Members",450,650);
    }

    public void goToSettings(ActionEvent actionEvent) {
        loadWindow("/sample/settings/Setting.fxml","Settings",300,350);
    }

    public void loadWindow(String path,String title,int height,int width){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(path));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(root,width,height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Invisible all the text.
    public void inVisibleText(){
        BookAuthor.setVisible(false);
        BookTitle.setVisible(false);
        BookStatus.setVisible(false);

        MemberName.setVisible(false);
        contactNoBox.setVisible(false);
    }

    //load book detail from database.
    public void loadBokInfo(ActionEvent actionEvent) {
        clearBookCache();
        String id = bookIdBox.getText();
        Book book = bookDb.getBookById(id);
        if(book!=null){
            BookTitle.setText(book.getTitle());
            BookAuthor.setText(book.getAuthor());

            String status = (book.getIsAvailable())?"Available":"Not Available";
            BookStatus.setText(status);

            BookStatus.setVisible(true);
            BookTitle.setVisible(true);
            BookAuthor.setVisible(true);
        }else{
            BookTitle.setText("No such book available");
            BookTitle.setVisible(true);
            BookAuthor.setVisible(false);
            BookStatus.setVisible(false);
        }
    }

    //load the member detail from database
    public void loadMemberInfo(ActionEvent actionEvent) {
        clearMemberCache();
        String id = memberIdBox.getText();
        Member member = memberDb.getMemberById(id);
        if(member!=null){
            MemberName.setText(member.getName());
            contactNoBox.setText(member.getMobileNo());
            MemberName.setVisible(true);
            contactNoBox.setVisible(true);
        }else{
            MemberName.setText("No such member exist!");
            MemberName.setVisible(true);
            contactNoBox.setVisible(false);
        }
    }

    //for clearing previously set text.
    public void clearBookCache(){
        BookTitle.setText("");
        BookStatus.setText("");
        BookAuthor.setText("");
    }

    //for clearing previously set text
    public void clearMemberCache(){
        MemberName.setText("");
        contactNoBox.setText("");
    }

    //Return Parent of this fxml
    public static Parent getRoot(){
        Parent root=null;
        try {
            root = FXMLLoader.load(Dashboard.class.getResource("Dashboard.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    //Issue book to member.
    public void issueBook(ActionEvent actionEvent) {
        String bookId = bookIdBox.getText();
        String memberId = memberIdBox.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure want to issue book "+BookTitle.getText()+" to "+MemberName.getText());

        Optional<ButtonType> response = alert.showAndWait();

        if (response.get()== ButtonType.OK){
            if(bookDb.issueBook(bookId,memberId)){
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Complete");
                alert1.setHeaderText(null);
                alert1.setContentText("Book is successfully issued");
                alert1.showAndWait();
            }else{
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setTitle("Failed");
                alert1.setHeaderText(null);
                alert1.setContentText("Book issue failed");
                alert1.showAndWait();
            }
        }else{
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Canceled");
            alert1.setHeaderText(null);
            alert1.setContentText("Book issue canceled");
            alert1.showAndWait();
        }
    }

    //Loading the book details for renew or submit
    public void loadBookForRenew(ActionEvent actionEvent) {
        String bookId = renewBookId.getText();
        issueBook = bookDb.getIssuedBookInfo(bookId);
        list.clear();
        if (issueBook!=null){
            Book book = bookDb.getBookById(bookId);
            Member member = memberDb.getMemberById(issueBook.getMemberId());
            list.add("********* Book Information **************");
            list.add("Book Title: "+book.getTitle());
            list.add("Book Author: "+book.getAuthor());
            list.add("Book Publisher: "+book.getPublisher());
            list.add(" ");
            list.add("********* Member Information ***********");
            list.add("Member Name: "+member.getName());
            list.add("Member Contact No. :"+member.getMobileNo());
            list.add(" ");
            list.add("***** Issue Details: *****");
            list.add("Issue Time : "+issueBook.getTimestamp().toGMTString());
            list.add("Renew Count: "+issueBook.getRenewCount());
        }
        bookList.getItems().setAll(list);
    }

    //Function for renewing book
    public void renewBook(ActionEvent actionEvent) {
        if (issueBook!=null){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to renew book "+issueBook.getBookId());

            Optional<ButtonType> optional = alert.showAndWait();

            if (optional.get()== ButtonType.OK){
                if(bookDb.renewBook(issueBook.getBookId(),issueBook.getRenewCount())){
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Successful");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book has been successfully renewed");
                    alert1.showAndWait();
                }else{
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Failed");
                    alert1.setContentText("Book renew operation failed.");
                    alert1.showAndWait();
                }
            }else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Cancelled");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Renew operation has been cancelled");
                alert1.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("This book is not issued to anyone.\nMake sure you have pressed Enter before pressing renew.");
            alert.showAndWait();
        }
    }

    //function for submitting the book
    public void submitBook(ActionEvent actionEvent) {
        if (issueBook!=null){

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure want to return the book "+issueBook.getBookId());

            Optional<ButtonType> optional = alert.showAndWait();

            if (optional.get()== ButtonType.OK){
                if(bookDb.submitBook(issueBook.getBookId())){
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setTitle("Successful");
                    alert1.setHeaderText(null);
                    alert1.setContentText("Book has been successfully returned");
                    alert1.showAndWait();
                }else{
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Failed");
                    alert1.setContentText("Book return operation failed.");
                    alert1.showAndWait();
                }
            }else{
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle("Cancelled");
                alert1.setHeaderText(null);
                alert1.setContentText("Book Return operation has been cancelled");
                alert1.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("This book is not issued to anyone.\nMake sure you have pressed Enter before pressing submit.");
            alert.showAndWait();
        }
    }

    public void handleLogout(ActionEvent actionEvent) {
        Stage stage = (Stage)memberIdBox.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/sample/login/Login.fxml"));
            stage.setTitle("Library Assistance Login");
            stage.setScene(new Scene(root,600,400));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleEditBook(ActionEvent actionEvent) {
        loadWindow("/sample/Books/EditBook.fxml","Edit Book Details",300,350);
    }

    public void handleEditMember(ActionEvent actionEvent) {
        loadWindow("/sample/Members/EditMember.fxml","Edit Member Details",300,350);
    }
}
