package printemps.core.discount;

import printemps.core.member.Member;

public interface DiscountPolicy {
  
  /*
   * @return ���� ��� �ݾ�
   */
  int discout(Member member, int price);
  
}
