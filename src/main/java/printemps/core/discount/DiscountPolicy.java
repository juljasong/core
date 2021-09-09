package printemps.core.discount;

import printemps.core.member.Member;

public interface DiscountPolicy {
  
  /*
   * @return 할인 대상 금액
   */
  int discout(Member member, int price);
  
}
