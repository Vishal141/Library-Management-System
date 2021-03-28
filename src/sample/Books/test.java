package sample.Books;

import com.google.gson.Gson;
import sample.requests.AddBookRequest;
import sample.requests.AllBookRequest;
import sample.requests.AllMemberRequest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class test {
    private static final String HTTP_URL = "http://localhost:8080/books";
    private static HttpURLConnection UrlConnection;
    private static Gson GSON = new Gson();
    private static String apiKey="ad5a";

    public static void main(String[] args) throws Exception {
        URL url = new URL(HTTP_URL+"/book/G001");
        UrlConnection = (HttpURLConnection)url.openConnection();
        UrlConnection.setRequestMethod("GET");
        UrlConnection.setDoOutput(true);
        UrlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

        AddBookRequest request = new AddBookRequest(apiKey,null);
        String json1 = GSON.toJson(request);
        UrlConnection.setFixedLengthStreamingMode(json1.length());
        OutputStream os = UrlConnection.getOutputStream();
        os.write(json1.getBytes());
        os.flush();
        os.close();

        BufferedReader reader = new BufferedReader(new InputStreamReader(UrlConnection.getInputStream()));
        String json = reader.readLine();

        AllBookRequest request1 = GSON.fromJson(json,AllBookRequest.class);
       // tmpbooks = request1.getBooks();
        System.out.println(json);
    }
}
