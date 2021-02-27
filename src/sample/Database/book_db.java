package sample.Database;

import sample.Books.Book;
import sample.Books.IssueBook;

import java.sql.*;
import java.util.ArrayList;

public class book_db {
    private Connection connection = null;
    private PreparedStatement preparedStatement,ps;
    private ResultSet resultSet;

    private static ArrayList<Book> books=null;

    private static int BookCount=0;

    //In the constructor we have got the instance of Connect
    public book_db(){
        Connect connect = Connect.getInstance();
        connection = connect.getConnection();
    }

    //Through this method we add book in the database
    public void add_book(Book book){
        String query = "INSERT INTO book VALUES(?,?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,book.getId());
            preparedStatement.setString(2,book.getTitle());
            preparedStatement.setString(3,book.getAuthor());
            preparedStatement.setString(4,book.getPublisher());
            preparedStatement.setBoolean(5,book.getIsAvailable());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //using this method we get all books present in the databse
    public ArrayList<Book> getBooks(){
        int count = getBookCount();
        if(books==null || count!=BookCount){
            BookCount = count;
            books = new ArrayList<Book>();
            String query = "SELECT * FROM book";
            try{
                preparedStatement = connection.prepareStatement((query));
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String id = resultSet.getString("id");
                    String title = resultSet.getString("title");
                    String author = resultSet.getString("author");
                    String publisher = resultSet.getString("publisher");
                    boolean isAvailable = resultSet.getBoolean("is_Avail");
                    Book book = new Book(id,title,author,publisher);
                    book.setIsAvailable(isAvailable);
                    books.add(book);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return books;
    }

    //this will return Book instance having bookId provided
    public Book getBookById(String id){
        String query = "SELECT * FROM book WHERE id=?";
        Book book = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String Id = resultSet.getString("id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                boolean status = resultSet.getBoolean("is_Avail");

                book = new Book(Id,title,author,publisher);
                book.setIsAvailable(status);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return book;
    }

   //This will issue the book with bookId to the member having id memberId.
    public boolean issueBook(String bookId,String memberId){
        String query1="INSERT INTO BookIssue(bookId,memberId) VALUES(?,?);UPDATE book SET is_Avail=? WHERE id=?";
        try{
            preparedStatement = connection.prepareStatement(query1);
            preparedStatement.setString(1,bookId);
            preparedStatement.setString(2,memberId);
            preparedStatement.setBoolean(3,false);
            preparedStatement.setString(4,bookId);
            System.out.println(preparedStatement.executeUpdate());
            return true;
        }catch (Exception e){
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
   public IssueBook getIssuedBookInfo(String bookId){
        String query = "SELECT * FROM BookIssue WHERE bookId=?";
        IssueBook issueBook=null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,bookId);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                String memberId = resultSet.getString("memberId");
                Timestamp timestamp = resultSet.getTimestamp("issueTime");
                int renewCount = resultSet.getInt("renewCount");

                issueBook = new IssueBook(bookId,memberId,timestamp,renewCount);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return issueBook;
   }

   //This will increase the renew count of book having id bookId.
   public boolean renewBook(String bookId,int renewCount){
        String query = "UPDATE BookIssue SET issueTime=CURRENT_TIMESTAMP,renewCount=? WHERE bookId=?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,renewCount+1);
            preparedStatement.setString(2,bookId);
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
   }

   //this will delete the book from BookIssue table and set availability true.
   public boolean submitBook(String bookId){
        String query = "DELETE FROM BookIssue WHERE bookId=?;UPDATE book SET is_Avail=? WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,bookId);
            preparedStatement.setBoolean(2,true);
            preparedStatement.setString(3,bookId);
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
   }

   //this will edit book details in database.
    public boolean editBookDetails(Book book){
        String query = "UPDATE book SET title=?,author=?,publisher=? WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,book.getTitle());
            preparedStatement.setString(2,book.getAuthor());
            preparedStatement.setString(3,book.getPublisher());
            preparedStatement.setString(4,book.getId());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //this will delete the book from database.
    public boolean deleteBook(String id){
        String query = "DELETE FROM book WHERE id=?";
        try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


   //this will return total number of books present in the database
    public int getBookCount(){
        String query="SELECT COUNT(*) FROM book";
        int count = 0;
        try{
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                count = resultSet.getInt("count(*)");
        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }

}
