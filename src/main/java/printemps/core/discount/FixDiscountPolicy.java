package printemps.core.discount;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import printemps.core.member.Grade;
import printemps.core.member.Member;

@Component
//@Qualifier("fixDiscountPolicy")
public class FixDiscountPolicy implements DiscountPolicy {

  private int discountFixAmount = 1000; // 1000원 할인

  @Override
  public int discount(Member member, int price) {

    if (member.getGrade() == Grade.VIP) { // Enum 타입은 ==
      return discountFixAmount;
    } else {
      return 0;
    }
  }

}
