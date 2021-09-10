package printemps.core;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import printemps.core.member.Grade;
import printemps.core.member.Member;
import printemps.core.member.MemberService;
import printemps.core.order.Order;
import printemps.core.order.OrderService;

public class OrderApp {
  
  public static void main(String[] args) {
    
    //AppConfig appConfig = new AppConfig();
    
    //MemberService memberService = new MemberServiceImpl();
    //OrderService orderService = new OrderServiceImpl();
    //MemberService memberService = appConfig.memberService();
    //OrderService orderService = appConfig.orderService();
    
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    OrderService orderService = ac.getBean("orderService", OrderService.class);
    
    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    //Member member = new Member(memberId, "memberA", Grade.BASIC);
    
    memberService.join(member);
    
    Order order = orderService.createOrder(memberId, "itemA", 20000);
    System.out.println(order);
    System.out.println(order.calculatePrice());
    
  }
}
