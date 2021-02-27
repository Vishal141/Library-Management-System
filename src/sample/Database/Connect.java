package sample.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {

    private Connection connection=null;
    private String url = "jdbc:mysql://localhost:3306/library_assistance?characterEncoding=utf-8&allowMultiQueries=true";

    private static Connect connect=null;


    private Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,"root","141252");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connect getInstance(){
        if(connect==null)
            connect = new Connect();
        return connect;
    }

    public Connection getConnection(){
        return connection;
    }



}
