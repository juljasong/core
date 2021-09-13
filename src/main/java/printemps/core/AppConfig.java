package printemps.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import printemps.core.discount.DiscountPolicy;
import printemps.core.discount.RateDiscountPolicy;
import printemps.core.member.MemberRepository;
import printemps.core.member.MemberService;
import printemps.core.member.MemberServiceImpl;
import printemps.core.member.MemoryMemberRepository;
import printemps.core.order.OrderService;
import printemps.core.order.OrderServiceImpl;

// DI(Dependency Injection)
// 설정정보
@Configuration // Configuration이 없으면 싱글톤 적용 X
public class AppConfig {
  
  @Bean
  public MemberService memberService() {
    //System.out.println("** call AppConfig.memberService");
    return new MemberServiceImpl(memberRepository());
  }

  @Bean
  public MemberRepository memberRepository() {
    //System.out.println("** call AppConfig.memberRepository");
    return new MemoryMemberRepository();
  }
  
  @Bean
  public OrderService orderService() {
    //System.out.println("** call AppConfig.orderService");
    return new OrderServiceImpl(memberRepository(), discountPolicy());
  }

  @Bean
  public DiscountPolicy discountPolicy() {
    return new RateDiscountPolicy();
  }

}
