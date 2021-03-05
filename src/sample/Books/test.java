package sample.Books;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class test {
    public static void main(String[] args) {
        String link = "http://localhost:8080/books/AllBooks";
        try {
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = reader.readLine();
            System.out.println(line);
            Gson gson = new Gson();
            TempBook[] books;
            books = gson.fromJson(line,TempBook[].class);
            System.out.println("\n");
            for(TempBook book:books)
                System.out.println(book.getIsAvailable());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
