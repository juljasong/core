package printemps.core;

import printemps.core.discount.DiscountPolicy;
import printemps.core.discount.FixDiscountPolicy;
import printemps.core.member.MemberRepository;
import printemps.core.member.MemberService;
import printemps.core.member.MemberServiceImpl;
import printemps.core.member.MemoryMemberRepository;
import printemps.core.order.OrderService;
import printemps.core.order.OrderServiceImpl;

// DI(Dependency Injection)
public class AppConfig {
  
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }

  private MemberRepository memberRepository() {
    return new MemoryMemberRepository();
  }
  
  public OrderService orderService() {
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  private DiscountPolicy discountPolicy() {
    return new FixDiscountPolicy();
  }

}
