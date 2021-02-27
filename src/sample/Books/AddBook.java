package sample.Books;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.Database.book_db;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddBook implements Initializable {

    @FXML
    JFXTextField bTitle,bId,bPublisher,bAuthor;

    private book_db bookDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookDb = new book_db();
    }

    public void addbook(ActionEvent actionEvent) {
        if(!(bTitle.getText()==null || bTitle.getText().equals("") ||
           bId.getText()==null || bId.getText().equals("") ||
           bPublisher.getText()==null || bPublisher.equals("") ||
           bAuthor.getText()==null || bAuthor.getText().equals(""))){

            Book book = new Book(bId.getText(),bTitle.getText(),bAuthor.getText(),bPublisher.getText());

            bookDb.add_book(book);  //adding book to database.

            Alert alert = new Alert(Alert.AlertType.INFORMATION);  //showing alert.
            alert.setContentText("Book is added successfully");
            alert.show();

            cancel(actionEvent);  //closing the stage.

        }else{
            warningAlert();
        }
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage) bId.getScene().getWindow();
        stage.close();
    }

    public void warningAlert(){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("all fields are mandatory.");
        alert.show();
    }


    public static Parent getParent(){
        Parent root=null;
        try {
            root = FXMLLoader.load(AddBook.class.getResource("add_book.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
