package sample.Books;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Controller;
import sample.Database.book_db;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookList implements Initializable {

    @FXML
    AnchorPane body;
    @FXML
    TableView<Book> tableView;
    @FXML
    TableColumn<Book,String> titleCol,idCol,authorCol,publisherCol;
    @FXML
    TableColumn<Book,Boolean> AvailabilityCol;

    private book_db bookDb;

    ObservableList<Book> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumn();
        bookDb = new book_db();

        list.addAll(bookDb.getBooks());

        tableView.getItems().setAll(list);

    }

    private void initColumn() {
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        AvailabilityCol.setCellValueFactory(new PropertyValueFactory<>("isAvailable"));
    }

    public static Parent getRoot(){
        Parent root=null;
        try {
            root = FXMLLoader.load(BookList.class.getResource("BookList.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    public void handleEditBook(ActionEvent actionEvent) {

        Book book = tableView.getSelectionModel().getSelectedItem();

        if (book==null){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Book is not selected.\nPlease select a book.");
            alert.showAndWait();
            return;
        }

        Stage stage = (Stage)body.getScene().getWindow();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditBook.fxml"));
            Parent root = loader.load();

            EditBook editBook = (EditBook)loader.getController();
            editBook.inflateData(book);

            stage.setTitle("Edit book details");
            stage.setScene(new Scene(root,350,270));
            Controller.setStageIcon(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void handleDeleteBook(ActionEvent actionEvent) {
        //fetching the selected item
        Book book = tableView.getSelectionModel().getSelectedItem();
        if (book!=null){

            IssueBook issueBook = bookDb.getIssuedBookInfo(book.getId());

            if (issueBook==null){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure want to delete book: "+book.getTitle());

                Optional<ButtonType> response = alert.showAndWait();
                if (response.get()==ButtonType.OK){
                    if (bookDb.deleteBook(book.getId())){
                        Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Deleted");
                        alert.setHeaderText(null);
                        alert.setContentText("Book is deleted successfully");
                        alert.showAndWait();

                        list.clear();
                        list.addAll(bookDb.getBooks());
                        tableView.getItems().setAll(list);

                    }
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText(null);
                alert.setContentText("This book is issued to someone you can't delete is right now.");
                alert.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText(null);
            alert.setContentText("Book is not selected.\nPlease select a book.");
            alert.showAndWait();
        }
    }
}
