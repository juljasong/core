package printemps.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import printemps.core.member.Grade;
import printemps.core.member.Member;
import printemps.core.member.MemberService;

public class MemberApp {
  
  public static void main(String[] args) {
    
    //AppConfig appConfig = new AppConfig();
    
    //MemberService memberService = new MemberService();
    //MemberService memberService = appConfig.memberService();
    
    // AppConfig�� @Bean ���� �� ����
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class); // ("�̸�", Ÿ��);
    
    Member member = new Member(1L, "memberA", Grade.VIP);
    
    memberService.join(member);
    
    System.out.println("New : " + member.getName());
    System.out.println("Find... : " + memberService.findMember(member.getId()).getName());
  }

}
