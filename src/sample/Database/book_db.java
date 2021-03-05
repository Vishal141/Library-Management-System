package sample.Database;

import com.google.gson.Gson;
import sample.Books.Book;
import sample.Books.IssueBook;
import sample.Books.TempBook;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;

public class book_db {
    private static final String HTTP_URL = "http://localhost:8080/books";
    private static final Gson GSON = new Gson();
    private HttpURLConnection UrlConnection=null;
    private static ArrayList<Book> books=null;

    //Through this method we add book in the database
    public void add_book(TempBook book){
        try {
            URL url = new URL(HTTP_URL+"/addBook");
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("POST");
            UrlConnection.setDoOutput(true);
            UrlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            String json = GSON.toJson(book);

            UrlConnection.setFixedLengthStreamingMode(json.length());
            OutputStream os = UrlConnection.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    //using this method we get all books present in the database
    public ArrayList<Book> getBooks(){
        TempBook[] tmpbooks;
        try {
            URL url = new URL(HTTP_URL+"/AllBooks");
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String line;
            String json = "";
            while ((line=reader.readLine())!=null){
                json += line;
            }

            tmpbooks = GSON.fromJson(json,TempBook[].class);

            books = new ArrayList<Book>();
            for(TempBook book:tmpbooks){
                Book tmp = new Book(book.getId(),book.getTitle(),book.getAuthor(),book.getPublisher());
                tmp.setIsAvailable(book.getIsAvailable());
                books.add(tmp);
            }

            return books;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }

//    //this will return Book instance having bookId provided

    public Book getBookById(String id){
        Book book=null;
        try {
            URL url = new URL(HTTP_URL+"/book/"+id);
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String json="";
            String line;
            while((line=reader.readLine())!=null)
                json += line;

            TempBook tempBook = GSON.fromJson(json,TempBook.class);

            book = new Book(tempBook.getId(),tempBook.getTitle(),tempBook.getAuthor(),tempBook.getPublisher());
            book.setIsAvailable(tempBook.getIsAvailable());

            return book;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return book;
    }

//   //This will issue the book with bookId to the member having id memberId.

    public boolean issueBook(String bookId,String memberId){
        try {
            URL url = new URL(HTTP_URL+"/issue"+"/"+bookId+"&"+memberId);
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String result = reader.readLine();

            return (result.equals("ISSUED"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

   //This will return instance of IssueBook having id bookId

    public IssueBook getIssuedBookInfo(String bookId){
        IssueBook issueBook=null;
        try {
            URL url = new URL(HTTP_URL+"/issue/book/"+bookId);
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String json="";
            String line;
            while((line=reader.readLine())!=null)
                json += line;

            issueBook = GSON.fromJson(json,IssueBook.class);

            return issueBook;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return issueBook;
    }

   //This will increase the renew count of book having id bookId.
   public boolean renewBook(String bookId,int renewCount){
       try {
           URL url = new URL(HTTP_URL+"/issue/renew/"+bookId);
           UrlConnection = (HttpURLConnection)url.openConnection();
           UrlConnection.setRequestMethod("GET");

           BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
           String result = reader.readLine();

           return (result.equals("RENEWED"));

       } catch (Exception e) {
           e.printStackTrace();
       }
       return false;
   }

   //this will delete the book from BookIssue table and set availability true.
   public boolean submitBook(String bookId){
       try {
           URL url = new URL(HTTP_URL+"/issue/return/"+bookId);
           UrlConnection = (HttpURLConnection)url.openConnection();
           UrlConnection.setRequestMethod("GET");

           BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
           String result = reader.readLine();

           return (result.equals("SUBMITTED"));

       } catch (Exception e) {
           e.printStackTrace();
       }
        return false;
   }

   //this will edit book details in database.
    public boolean editBookDetails(TempBook book){
        try {
            URL url = new URL(HTTP_URL+"/edit");
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");
            UrlConnection.setDoOutput(true);
            UrlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            String json = GSON.toJson(book);

            UrlConnection.setFixedLengthStreamingMode(json.length());
            OutputStream os = UrlConnection.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String result = reader.readLine();

            return (result.equals("EDITED"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //this will delete the book from database.
    public boolean deleteBook(String id){
        try {
            URL url = new URL(HTTP_URL+"/delete/"+id);
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String result = reader.readLine();

            return (result.equals("DELETED"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


   //this will return total number of books present in the database
    public int getBookCount(){
        try {
            URL url = new URL(HTTP_URL+"/count");
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String line = reader.readLine();
            int count = Integer.parseInt(line);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books.size();
    }

}
