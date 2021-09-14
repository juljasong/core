package printemps.core.discount;

import org.springframework.stereotype.Component;
import printemps.core.member.Grade;
import printemps.core.member.Member;

@Component
public class RateDiscountPolicy implements DiscountPolicy{
  
  private int discountPercent = 10;

  @Override
  public int discout(Member member, int price) {
    if (member.getGrade() == Grade.VIP) {
      return price * discountPercent / 100;
    } else {
      return 0;
    }
  }

}
