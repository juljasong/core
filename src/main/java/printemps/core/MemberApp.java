package printemps.core;

import printemps.core.member.Grade;
import printemps.core.member.Member;
import printemps.core.member.MemberService;
import printemps.core.member.MemberServiceImpl;

public class MemberApp {
  
  public static void main(String[] args) {
    
    MemberService memberService = new MemberServiceImpl();
    
    Member member = new Member(1L, "memberA", Grade.VIP);
    
    memberService.join(member);
    
    System.out.println("New : " + member.getName());
    System.out.println("Find... : " + memberService.findMember(member.getId()).getName());
  }

}
