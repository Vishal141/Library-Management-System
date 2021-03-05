package library.management.LibraryManagement;

import library.management.LibraryManagement.Books.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greet {
    
    @RequestMapping("/book/{id}")
    public Book sayHello(@PathVariable String id){
        if(id.equals("H001"))
            return new Book("H001","Harry Potter","J.K. Rowling","Bloomsbury");
        return new Book();
    }   
}
