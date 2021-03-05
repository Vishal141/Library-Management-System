package sample.Database;

import com.google.gson.Gson;
import sample.Books.Book;
import sample.Members.Member;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDb {

    private static final String HTTP_URL = "http://localhost:8080/members";

    private HttpURLConnection UrlConnection=null;

    private static final Gson GSON = new Gson();

    private Connection connection = null;
    private PreparedStatement preparedStatement=null;
    private ResultSet resultSet;

    private static ArrayList<Member> members=null;
    private static int MemberCount=0;

    public MemberDb(){
        Connect connect = Connect.getInstance();
        connection = connect.getConnection();

    }

    //this will add a member in database.
    public void addMember(Member member){
        try {
            URL url = new URL(HTTP_URL+"/addBook");
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("POST");
            UrlConnection.setDoOutput(true);
            UrlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            String json = GSON.toJson(member);

            UrlConnection.setFixedLengthStreamingMode(json.length());
            OutputStream os = UrlConnection.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //this will return a list of all the member.
    public ArrayList<Member> getMembers(){
        String query="SELECT * FROM member";
        int count = getMemberCount();
        if (count!=MemberCount || members==null){
            MemberCount = count;
            members = new ArrayList<Member>();
            try {
                preparedStatement = connection.prepareStatement(query);
                resultSet = preparedStatement.executeQuery();
                while (resultSet.next()){
                    String id = resultSet.getString("id");
                    String name = resultSet.getString("name");
                    String email = resultSet.getString("email");
                    String mobile = resultSet.getString("contactNo");

                    Member member = new Member(name,id,email,mobile);

                    members.add(member);

                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return members;
    }

    //this will return a member instance having id equal to id provided in argument.
    public Member getMemberById(String id){
        String query = "SELECT * FROM member WHERE id=?";
        Member member = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                String Id = resultSet.getString("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String mobile = resultSet.getString("contactNo");

                member = new Member(name,Id,email,mobile);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return member;
    }

    //this will edit member details.
    public boolean editMemberDetails(Member member){
        String query = "UPDATE member SET name=?,email=?,contactNo=? WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,member.getName());
            preparedStatement.setString(2,member.getEmailId());
            preparedStatement.setString(3,member.getMobileNo());
            preparedStatement.setString(4,member.getId());
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //This will delete a member from database.
    public boolean deleteMember(String id){
        String query = "DELETE FROM member WHERE id=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            preparedStatement.executeUpdate();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

    //this will check weather a user issued a book or not.
    public boolean hasIssued(String id){
        String query = "SELECT * FROM BookIssue WHERE memberId=?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,id);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //that will return total number of member in database.
    public int getMemberCount(){
        String query="SELECT COUNT(*) FROM member";
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt("count(*)");
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }
}
