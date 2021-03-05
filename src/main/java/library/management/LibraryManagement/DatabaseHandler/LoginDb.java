package library.management.LibraryManagement.DatabaseHandler;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.springframework.stereotype.Service;

@Service
public class LoginDb {
    private Connection connection=null;
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet;

    public LoginDb(){
        ConnectDatabase connect = ConnectDatabase.getInstance();
         connection = connect.getConnection();
    }

    //for getting hash value of password.
    private String getMd5(String input){
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1,messageDigest);
            String hash=number.toString();
            while (hash.length()<32){
                hash = "v"+hash;
            }
            return hash;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean login(String username,String password){
        //String hash = getMd5(password);
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean register(String name,String username,String email,String password){
        //String hash = getMd5(password);
        String query = "INSERT INTO users VALUES(?,?,?,?)";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,username);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,password);
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //that will edit preferences of library rules.
//    public boolean editPreferences(int noOfDays,float finePerDay,String username,String password){
//        if (login(username,password)){
//            Preferences preferences = new Preferences();
//            preferences.setNoOfDays(noOfDays);
//            preferences.setFinePerDay(finePerDay);
//            String query = "UPDATE preferences SET noOfDays=?,finePerDay=?";
//            try {
//                preparedStatement = connection.prepareStatement(query);
//                preparedStatement.setInt(1,noOfDays);
//                preparedStatement.setFloat(2,finePerDay);
//                preparedStatement.executeUpdate();
//                return true;
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }else{
//            return false;
//        }
//        return false;
//    }
}
