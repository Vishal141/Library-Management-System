package sample.Database;

import com.google.gson.Gson;

import sample.Members.Member;
import sample.Members.TempMember;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    private static ArrayList<Member> members=null;


    //this will add a member in database.
    public void addMember(TempMember member){
        try {
            URL url = new URL(HTTP_URL+"/addMember");
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
        TempMember[] tempMembers;
        try {
            URL url = new URL(HTTP_URL+"/AllMembers");
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String line;
            String json = "";
            while ((line=reader.readLine())!=null){
                json += line;
            }

            tempMembers = GSON.fromJson(json,TempMember[].class);

            members = new ArrayList<Member>();
            for(TempMember member:tempMembers){
                Member member1 = new Member(member.getName(),member.getId(),member.getEmailId(),member.getMobileNo());
                members.add(member1);
            }

            return members;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }

    //this will return a member instance having id equal to id provided in argument.
    public Member getMemberById(String id){
        Member member=null;
        try {
            URL url = new URL(HTTP_URL+"/member/"+id);
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String json="";
            String line;
            while((line=reader.readLine())!=null)
                json += line;

            TempMember tempMember = GSON.fromJson(json,TempMember.class);

            member = new Member(tempMember.getName(),tempMember.getId(),tempMember.getEmailId(),tempMember.getMobileNo());

            return member;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return member;
    }

    //this will edit member details.
    public boolean editMemberDetails(TempMember member){
        try {
            URL url = new URL(HTTP_URL+"/edit");
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");
            UrlConnection.setDoOutput(true);
            UrlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            String json = GSON.toJson(member);

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

    //This will delete a member from database.
    public boolean deleteMember(String id){
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

    //this will check weather a user issued a book or not.
    public boolean hasIssued(String id){
        try {
            URL url = new URL(HTTP_URL+"/member/hasIssued/"+id);
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

    //that will return total number of member in database.
    public int getMemberCount(){
        try {
            URL url = new URL(HTTP_URL+"/count");
            UrlConnection = (HttpURLConnection)url.openConnection();
            UrlConnection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
            String result = reader.readLine();

            return Integer.parseInt(result);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
