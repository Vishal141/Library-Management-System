package sample.Books;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.Database.book_db;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditBook implements Initializable {
    @FXML
    JFXTextField bId,bTitle,bAuthor,bPublisher;

    private book_db bookDb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        bookDb = new book_db();
    }

    public void save(ActionEvent actionEvent) {
        String id = bId.getText();
        if (id==null)
            return;
        Book book = bookDb.getBookById(id);
        if(book!=null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("Confirm");
            alert.setContentText("Are sure want to edit details of book: "+book.getTitle());

            Optional<ButtonType> response = alert.showAndWait();

            if(response.get()==ButtonType.OK){
                if (bTitle!=null && !bTitle.getText().equals(""))
                    book.setTitle(bTitle.getText());
                if (bAuthor!=null && !bAuthor.getText().equals(""))
                    book.setAuthor(bAuthor.getText());
                if(bPublisher!=null && !bPublisher.getText().equals(""))
                    book.setPublisher(bPublisher.getText());
                TempBook book1 = new TempBook(book.getId(),book.getTitle(),book.getAuthor(),book.getPublisher(),book.getIsAvailable());
                if(bookDb.editBookDetails(book1)){
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.setHeaderText(null);
                    alert1.setTitle("Successful");
                    alert1.setContentText("Book details are successfully updated");
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
                alert1.setContentText("update operation is failed");
                alert1.showAndWait();
            }

        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setTitle("Warning");
            alert.setContentText("No such book is available");
            alert.showAndWait();
        }
    }

    public void inflateData(Book book){
        bId.setText(book.getId());
        bTitle.setText(book.getTitle());
        bAuthor.setText(book.getAuthor());
        bPublisher.setText(book.getPublisher());
    }

    public void cancel(ActionEvent actionEvent) {
        Stage stage = (Stage)bId.getScene().getWindow();
        stage.close();
    }
}
