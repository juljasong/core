package printemps.core.discount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import printemps.core.member.Grade;
import printemps.core.member.Member;

public class RateDiscountPolicyTest {
  
  RateDiscountPolicy rateDiscountPolicy = new RateDiscountPolicy();
  
  @Test
  @DisplayName("VIP�� 10% ������ ����Ǿ�� �Ѵ�") 
  void vip_o() {
    //given
    Member member = new Member(1L, "memberVIP", Grade.VIP);
    
    //when
    int discount = rateDiscountPolicy.discout(member, 10000);
    
    //then
    Assertions.assertThat(discount).isEqualTo(1000);
  }
  
  @Test
  @DisplayName("VIP�� �ƴϸ� ������ ������� �ʾƾ� �Ѵ�") 
  void vip_x() {
    //given
    Member member = new Member(2L, "memberBASIC", Grade.BASIC);
    
    //when
    int discount = rateDiscountPolicy.discout(member, 10000);
    
    //then
    Assertions.assertThat(discount).isEqualTo(0);
  }

}
