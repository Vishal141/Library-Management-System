package library.management.LibraryManagement.DatabaseHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

public class ConnectDatabase {

    //private static final String URL = "jdbc:mysql://localhost:3306/library_assistance?characterEncoding=utf-8&allowMultiQueries=true";

    //private Connection connection=null;
    //private static ConnectDatabase connect=null;
    
    private static HashMap<String,Connection> connections = new HashMap<String,Connection>();
    
    private static Connection connectToDatabase(String apiKey){
        try{
            String url = "jdbc:mysql://localhost:3306/"+apiKey+"?characterEncoding=utf-8&allowMultiQueries=true";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url,"root","141252");
            connections.put(apiKey, connection);
            return connection;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Connection getInstance(String apiKey){
        Connection connect = connections.get(apiKey);
        if(connect==null){
            return connectToDatabase(apiKey);
        }
        return connect;
    }

//    public Connection getConnection(){
//        return connection;
//    }
    
}
