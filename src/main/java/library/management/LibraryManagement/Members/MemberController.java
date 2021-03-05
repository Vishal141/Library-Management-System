package library.management.LibraryManagement.Members;

import java.util.ArrayList;
import library.management.LibraryManagement.DatabaseHandler.MemberDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public class MemberController {
    
    @Autowired
    private MemberDb memberDb;
    
    @RequestMapping(method=RequestMethod.POST,value="/members/addMember")
    public void addMember(@RequestBody Member member){
        memberDb.addMember(member);
    }
    
    @RequestMapping("/members/member/{id}")
    public Member getMemberById(@PathVariable String id){
        return memberDb.getMemberById(id);
    }
    
    @RequestMapping("/members/AllMembers")
    public ArrayList<Member> getAllMembers(){
        return memberDb.getMembers();
    }
    
    @RequestMapping("/members/edit")
    public String editMemberDetails(@RequestBody Member member){
        boolean result = memberDb.editMemberDetails(member);
        if(result)
            return "EDITED";
        return "FAILED";
    }
    
    @RequestMapping("/members/delete/{id}")
    public String deleteMember(@PathVariable String id){
        boolean result = memberDb.deleteMember(id);
        if(result)
            return "DELETED";
        return "FAILED";
    }
    
    @RequestMapping("/members/member/hasIssued/{id}")
    public String hasIssued(@PathVariable String id){
        boolean result = memberDb.hasIssued(id);
        if(result)
            return "ISSUED";
        return "NO";
    }
    
    @RequestMapping("/members/count")
    public String getMemberCount(){
        return memberDb.getMemberCount()+"";
    }
        
    
}
