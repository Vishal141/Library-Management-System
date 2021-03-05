package library.management.LibraryManagement.Login;

import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;

import library.management.LibraryManagement.DatabaseHandler.LoginDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class LoginController {
    
    @Autowired
    private LoginDb loginDb;
    
    @RequestMapping("/login/{username}&{password}")
    public String login(@PathVariable String username,@PathVariable String password){
        boolean result = loginDb.login(username, password);
        if(result)
            return "ACCEPT";
        return "REJECT";
    }
    
    @RequestMapping("/signUp")
    public String register(@RequestBody HashMap<String,String> map){
        boolean result = loginDb.register(map.get("name"),map.get("username"),map.get("email"),map.get("password"));
        if(result)
            return "ACCEPT";
        return "REJECT";
    }
    
}
