package library.management.LibraryManagement.Login;

import java.util.HashMap;
import org.springframework.web.bind.annotation.RestController;

import library.management.LibraryManagement.DatabaseHandler.LoginDb;
import library.management.LibraryManagement.requests.SetPreferencesRequest;
import library.management.LibraryManagement.requests.SignInRequest;
import library.management.LibraryManagement.requests.SignUpRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class LoginController {
    
    @Autowired
    private LoginDb loginDb;
    
    @RequestMapping("/login")
    public String login(@RequestBody SignInRequest request){
        boolean result = loginDb.login(request.getUsername(),request.getPassword(),request.getApiKey());
        if(result)
            return "ACCEPT";
        return "REJECT";
    }
    
    @RequestMapping("/signUp")
    public String register(@RequestBody SignUpRequest request){
        boolean result = loginDb.register(request.getName(),request.getEmail(),request.getUsername(),request.getPassword(),request.getApiKey());
        if(result)
            return "ACCEPT";
        return "REJECT";
    }
    
    @RequestMapping("/preferences/get")
    public SetPreferencesRequest getPreferences(@RequestBody SetPreferencesRequest request){
        SetPreferencesRequest re = new SetPreferencesRequest("dkaa5d6",loginDb.getPreferences(request.getApiKey()));
        return re;
    }
    
    @RequestMapping("/preferences/edit")
    public String editPreferences(@RequestBody SetPreferencesRequest request){
        Preferences preferences = request.getPreferences();
        boolean result = loginDb.login(preferences.getUsername(),preferences.getPassword(),request.getApiKey());
        if(result){
            boolean ans = loginDb.editPreferences(preferences,request.getApiKey());
            if(ans)
                return "EDITED";
        }
        
        return "FAILED";
    }
    
}