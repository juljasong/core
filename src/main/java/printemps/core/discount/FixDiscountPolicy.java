package printemps.core.discount;

import printemps.core.member.Grade;
import printemps.core.member.Member;

public class FixDiscountPolicy implements DiscoutPolicy {

  private int discountFixAmount = 1000; // 1000원 할인

  @Override
  public int discout(Member member, int price) {

    if (member.getGrade() == Grade.VIP) { // Enum 타입은 ==
      return discountFixAmount;
    } else {
      return 0;
    }
  }

}
