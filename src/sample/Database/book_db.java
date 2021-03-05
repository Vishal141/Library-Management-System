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

    private Connection connection = null;
    private PreparedStatement preparedStatement,ps;
    private ResultSet resultSet;

    private HttpURLConnection UrlConnection=null;

    private static ArrayList<Book> books=null;

    private static int BookCount=0;

    //In the constructor we have got the instance of Connect
    public book_db(){
        Connect connect = Connect.getInstance();
        connection = connect.getConnection();
    }

    //Through this method we add book in the database
//    public void add_book(Book book){
//        String query = "INSERT INTO book VALUES(?,?,?,?,?)";
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1,book.getId());
//            preparedStatement.setString(2,book.getTitle());
//            preparedStatement.setString(3,book.getAuthor());
//            preparedStatement.setString(4,book.getPublisher());
//            preparedStatement.setBoolean(5,book.getIsAvailable());
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

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

//    //using this method we get all books present in the databse
//    public ArrayList<Book> getBooks(){
//        int count = getBookCount();
//        if(books==null || count!=BookCount){
//            BookCount = count;
//            books = new ArrayList<Book>();
//            String query = "SELECT * FROM book";
//            try{
//                preparedStatement = connection.prepareStatement((query));
//                resultSet = preparedStatement.executeQuery();
//                while (resultSet.next()){
//                    String id = resultSet.getString("id");
//                    String title = resultSet.getString("title");
//                    String author = resultSet.getString("author");
//                    String publisher = resultSet.getString("publisher");
//                    boolean isAvailable = resultSet.getBoolean("is_Avail");
//                    Book book = new Book(id,title,author,publisher);
//                    book.setIsAvailable(isAvailable);
//                    books.add(book);
//                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//
//        return books;
//    }

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
//    public Book getBookById(String id){
//        String query = "SELECT * FROM book WHERE id=?";
//        Book book = null;
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1,id);
//            resultSet = preparedStatement.executeQuery();
//            if(resultSet.next()){
//                String Id = resultSet.getString("id");
//                String title = resultSet.getString("title");
//                String author = resultSet.getString("author");
//                String publisher = resultSet.getString("publisher");
//                boolean status = resultSet.getBoolean("is_Avail");
//
//                book = new Book(Id,title,author,publisher);
//                book.setIsAvailable(status);
//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return book;
//    }

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
//    public boolean issueBook(String bookId,String memberId){
//        String query1="INSERT INTO BookIssue(bookId,memberId) VALUES(?,?);UPDATE book SET is_Avail=? WHERE id=?";
//        try{
//            preparedStatement = connection.prepareStatement(query1);
//            preparedStatement.setString(1,bookId);
//            preparedStatement.setString(2,memberId);
//            preparedStatement.setBoolean(3,false);
//            preparedStatement.setString(4,bookId);
//            System.out.println(preparedStatement.executeUpdate());
//            return true;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return false;
//    }

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

    //This set the Availability of the book.
   public boolean setAvailability(String bookId,Boolean type){
       String query2="UPDATE book SET is_Avail=? WHERE id=?";
       try{
           preparedStatement = connection.prepareStatement(query2);
           preparedStatement.setBoolean(1,type);
           preparedStatement.setString(2,bookId);

           if(preparedStatement.executeUpdate()==1){
               return true;
           }

       }catch (Exception e){
           e.printStackTrace();
       }
       return false;
   }

   //This will return instance of IssueBook having id bookId
//   public IssueBook getIssuedBookInfo(String bookId){
//        String query = "SELECT * FROM BookIssue WHERE bookId=?";
//        IssueBook issueBook=null;
//        try {
//            preparedStatement = connection.prepareStatement(query);
//            preparedStatement.setString(1,bookId);
//            resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()){
//                String memberId = resultSet.getString("memberId");
//                Timestamp timestamp = resultSet.getTimestamp("issueTime");
//                int renewCount = resultSet.getInt("renewCount");
//
//                issueBook = new IssueBook(bookId,memberId,timestamp,renewCount);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return issueBook;
//   }

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
