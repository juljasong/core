package printemps.core.discount;

import printemps.core.member.Member;

public interface DiscoutPolicy {
  
  /*
   * @return ���� ��� �ݾ�
   */
  int discout(Member member, int price);
  
}
