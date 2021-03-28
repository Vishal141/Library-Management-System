package library.management.LibraryManagement.Books;

import java.util.ArrayList;

import library.management.LibraryManagement.DatabaseHandler.BookDb;
import library.management.LibraryManagement.requests.AddBookRequest;
import library.management.LibraryManagement.requests.AllBookRequest;
import library.management.LibraryManagement.requests.DeleteBookRequest;
import library.management.LibraryManagement.requests.IssueBookRequest;
import library.management.LibraryManagement.requests.SubmitRenewRequest;

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
    public void add_book(@RequestBody AddBookRequest request){
        bookDb.add_book(request.getBook(),request.getApiKey());
    }
    
    @RequestMapping("/books/AllBooks")
    public AllBookRequest getAllBooks(@RequestBody AllBookRequest r){
        AllBookRequest request = new AllBookRequest("52a4d46",bookDb.getBooks(r.getApiKey()));
        return request;
    }
    
    @RequestMapping("/books/book/{id}")
    public AddBookRequest getBook(@RequestBody AddBookRequest request ,@PathVariable String id){
        AddBookRequest request1 = new AddBookRequest("5a4da6d2",bookDb.getBookById(id,request.getApiKey()));
        return request1;
    }
    
    @RequestMapping("/books/issue")
    public String issueBook(@RequestBody IssueBookRequest request){
        boolean result = bookDb.issueBook(request.getBookId(), request.getMemberId(),request.getApiKey());
        if(result)
            return "ISSUED";
        return "FAILED";
    }
    
    @RequestMapping("/books/issue/book")
    public IssueBookRequest getIssueBookInfo(@RequestBody IssueBookRequest request){
        IssueBookRequest r = new IssueBookRequest("5d5dad45a",request.getBookId(),null,bookDb.getIssuedBookInfo(request.getBookId(),request.getApiKey()));
        return r;
    }
    
    @RequestMapping("/books/issue/renew")
    public String renewBook(@RequestBody SubmitRenewRequest request){
        boolean result = bookDb.renewBook(request.getBookId(),request.getApiKey());
        if(result)
            return "RENEWED";
        return "FAILED";
    }
    
    @RequestMapping("/books/issue/return")
    public String submitBook(@RequestBody SubmitRenewRequest request){
        boolean result = bookDb.submitBook(request.getBookId(),request.getApiKey());
        if(result)
            return "SUBMITTED";
        return "FAILED";
    }
    
    @RequestMapping("/books/edit")
    public String editBookDetails(@RequestBody AddBookRequest request){
        boolean result = bookDb.editBookDetails(request.getBook(),request.getApiKey());
        if(result)
            return "EDITED";
        return "FAILED";
    }
    
    @RequestMapping("/books/delete")
    public String deleteBook(@RequestBody DeleteBookRequest request){
        boolean result = bookDb.deleteBook(request.getBookId(),request.getApiKey());
        if(result)
            return "DELETED";
        return "FAILED";
    }
    
    @RequestMapping("/books/count")
    public String getBookcount(){
        return (bookDb.getBookCount())+"";
    }
    
    
}