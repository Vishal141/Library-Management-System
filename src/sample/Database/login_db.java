package sample.Database;

import com.google.gson.Gson;
import sample.requests.SetPreferencesRequest;
import sample.requests.SignInRequest;
import sample.requests.SignUpRequest;
import sample.settings.Preferences;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class login_db {

    private static final String HTTP_URL = "http://localhost:8080/";
    private static String apiKey = "library_assistance";

    private HttpURLConnection httpConnection=null;


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
        String pass = getMd5(password);
        try {
            URL url = new URL(HTTP_URL+"/login");
            httpConnection =(HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            SignInRequest request = new SignInRequest(username,pass,apiKey);
            Gson gson = new Gson();
            String json = gson.toJson(request);

            httpConnection.setFixedLengthStreamingMode(json.length());
            OutputStream os = httpConnection.getOutputStream();
            os.write(json.getBytes());
            os.flush();
            os.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String result = reader.readLine();
            return (result.equals("ACCEPT"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean register(String name,String username,String email,String password){
        try {
            URL url = new URL(HTTP_URL+"/signUp");
            httpConnection = (HttpURLConnection)url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            SignUpRequest request = new SignUpRequest(name,username,email,getMd5(password),apiKey);

            Gson gson = new Gson();
            String json = gson.toJson(request);

            httpConnection.setFixedLengthStreamingMode(json.length());
            OutputStream out = httpConnection.getOutputStream();
            out.write(json.getBytes());
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String result = reader.readLine();

            return (result.equals("ACCEPT"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    //that will edit preferences of library rules.
    public boolean editPreferences(Preferences preferences){
        String pass = getMd5(preferences.getPassword());
        preferences.setPassword(pass);
        try {
            URL url = new URL(HTTP_URL+"/preferences/edit");
            httpConnection =(HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setDoOutput(true);

            SetPreferencesRequest request = new SetPreferencesRequest(apiKey,preferences);

            Gson gson = new Gson();
            String json = gson.toJson(request);
            httpConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpConnection.setFixedLengthStreamingMode(json.length());
            OutputStream os = httpConnection.getOutputStream();

            os.write(json.getBytes());
            os.flush();
            os.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String result = reader.readLine();
            return (result.equals("EDITED"));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
