package sample.Books;

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
import sample.Database.book_db;
import sample.dashboard.Dashboard;

import java.io.IOException;
import java.net.URL;
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


    ObservableList<Book> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumn();
        book_db bookDb = new book_db();

        list.addAll(bookDb.getBooks());

        tableView.setItems(list);

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
}
