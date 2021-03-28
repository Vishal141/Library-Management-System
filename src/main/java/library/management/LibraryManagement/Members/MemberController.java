package library.management.LibraryManagement.Members;

import java.util.ArrayList;
import library.management.LibraryManagement.DatabaseHandler.MemberDb;
import library.management.LibraryManagement.requests.AddMemberRequest;
import library.management.LibraryManagement.requests.AllMemberRequest;
import library.management.LibraryManagement.requests.DeleteMemberRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberController {
    
    @Autowired
    private MemberDb memberDb;
    
    @RequestMapping(method=RequestMethod.POST,value="/members/addMember")
    public void addMember(@RequestBody AddMemberRequest request){
        memberDb.addMember(request.getMember(),request.getApiKey());
    }
    
    @RequestMapping("/members/member/{id}")
    public AddMemberRequest getMemberById(@RequestBody AddMemberRequest request ,@PathVariable String id){
        AddMemberRequest re = new AddMemberRequest("da5d5ad",memberDb.getMemberById(id,request.getApiKey()));
        return re;
    }
    
    @RequestMapping("/members/AllMembers")
    public AllMemberRequest getAllMembers(@RequestBody AllMemberRequest request){
        AllMemberRequest r = new AllMemberRequest("5da6d5a",memberDb.getMembers(request.getApiKey()));
        return r;
    }
    
    @RequestMapping("/members/edit")
    public String editMemberDetails(@RequestBody AddMemberRequest request){
        boolean result = memberDb.editMemberDetails(request.getMember(),request.getApiKey());
        if(result)
            return "EDITED";
        return "FAILED";
    }
    
    @RequestMapping("/members/delete")
    public String deleteMember(@RequestBody DeleteMemberRequest request){
        boolean result = memberDb.deleteMember(request.getMemberId(),request.getApiKey());
        if(result)
            return "DELETED";
        return "FAILED";
    }
    
    @RequestMapping("/members/member/hasIssued")
    public String hasIssued(@RequestBody DeleteMemberRequest request){
        boolean result = memberDb.hasIssued(request.getMemberId(),request.getApiKey());
        if(result)
            return "ISSUED";
        return "NO";
    }
    
    @RequestMapping("/members/count")
    public String getMemberCount(){
        return memberDb.getMemberCount()+"";
    }
        
    
}