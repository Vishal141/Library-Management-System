package library.management.LibraryManagement;

import library.management.LibraryManagement.Books.Book;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Greet {
    
    @RequestMapping("/greet")
    public String sayHello(){
        return "Hello";
    }   
}
