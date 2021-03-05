package library.management.LibraryManagement.Books;

import java.util.ArrayList;

import library.management.LibraryManagement.DatabaseHandler.BookDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {
    
    @Autowired
    private BookDb bookDb;
    
    @RequestMapping(method=RequestMethod.POST,value="/books/addBook")
    public void add_book(@RequestBody Book book){
        bookDb.add_book(book);
    }
    
    @RequestMapping("/books/AllBooks")
    public ArrayList<Book> getAllBooks(){
        return bookDb.getBooks();
    }
    
    @RequestMapping("/books/book/{id}")
    public Book getBook(@PathVariable String id){
        return bookDb.getBookById(id);
    }
    
    @RequestMapping("/books/issue/{bookId}&{memberId}")
    public String issueBook(@PathVariable String bookId,@PathVariable String memberId){
        boolean result = bookDb.issueBook(bookId, memberId);
        if(result)
            return "ISSUED";
        return "FAILED";
    }
    
    @RequestMapping("/books/issue/book/{bookId}")
    public IssueBook getIssueBookInfo(@PathVariable String bookId){
        return bookDb.getIssuedBookInfo(bookId);
    }
    
    @RequestMapping("/books/issue/renew/{bookId}")
    public String renewBook(@PathVariable String bookId){
        boolean result = bookDb.renewBook(bookId);
        if(result)
            return "RENEWED";
        return "FAILED";
    }
    
    @RequestMapping("/books/issue/return/{bookId}")
    public String submitBook(@PathVariable String bookId){
        boolean result = bookDb.submitBook(bookId);
        if(result)
            return "SUBMITTED";
        return "FAILED";
    }
    
    @RequestMapping("/books/edit")
    public String editBookDetails(@RequestBody Book book){
        boolean result = bookDb.editBookDetails(book);
        if(result)
            return "EDITED";
        return "FAILED";
    }
    
    @RequestMapping("/books/delete/{bookId}")
    public String deleteBook(@PathVariable String bookId){
        boolean result = bookDb.deleteBook(bookId);
        if(result)
            return "DELETED";
        return "FAILED";
    }
    
    @RequestMapping("/books/count")
    public String getBookcount(){
        return (bookDb.getBookCount())+"";
    }
    
    
}
