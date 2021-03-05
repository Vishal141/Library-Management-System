package library.management.LibraryManagement.DatabaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDatabase {

    private static final String URL = "jdbc:mysql://localhost:3306/library_assistance?characterEncoding=utf-8&allowMultiQueries=true";

    private Connection connection=null;
    private static ConnectDatabase connect=null;


    private ConnectDatabase(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(URL,"root","141252");
            System.out.println("Conntected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ConnectDatabase getInstance(){
        if(connect==null)
            connect = new ConnectDatabase();
        return connect;
    }

    public Connection getConnection(){
        return connection;
    }
    
}
