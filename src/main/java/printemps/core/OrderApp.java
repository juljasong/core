package printemps.core;

import printemps.core.member.Grade;
import printemps.core.member.Member;
import printemps.core.member.MemberService;
import printemps.core.member.MemberServiceImpl;
import printemps.core.order.Order;
import printemps.core.order.OrderService;
import printemps.core.order.OrderServiceImpl;

public class OrderApp {
  
  public static void main(String[] args) {
    
    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();
    
    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    //Member member = new Member(memberId, "memberA", Grade.BASIC);
    
    memberService.join(member);
    
    Order order = orderService.createOrder(memberId, "itemA", 10000);
    System.out.println(order);
    System.out.println(order.calculatePrice());
    
  }
}
