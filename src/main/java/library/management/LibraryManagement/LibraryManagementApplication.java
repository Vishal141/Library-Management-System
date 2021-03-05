package library.management.LibraryManagement;

import library.management.LibraryManagement.DatabaseHandler.ConnectDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication
@SpringBootApplication(exclude =  {DataSourceAutoConfiguration.class })
public class LibraryManagementApplication {

	public static void main(String[] args) {
            ConnectDatabase connect = ConnectDatabase.getInstance();
	    SpringApplication.run(LibraryManagementApplication.class, args);
	}

}
