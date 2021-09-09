package printemps.core.order;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import printemps.core.AppConfig;
import printemps.core.member.Grade;
import printemps.core.member.Member;
import printemps.core.member.MemberService;

public class OrderServiceTest {
  
  MemberService memberService;
  OrderService orderService;
  
  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig();
    memberService = appConfig.memberService();
    orderService = appConfig.orderService();
  }
      
  @Test
  void createOrder() {
    Long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);
    
    Order order = orderService.createOrder(memberId, "itemA", 12000);
    Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1200); // OK
  }
  

}
