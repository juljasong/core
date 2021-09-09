package printemps.core.discount;

import printemps.core.member.Member;

public interface DiscoutPolicy {
  
  /*
   * @return 할인 대상 금액
   */
  int discout(Member member, int price);
  
}
