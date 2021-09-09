package printemps.core.order;

import printemps.core.discount.DiscoutPolicy;
import printemps.core.discount.FixDiscountPolicy;
import printemps.core.member.Member;
import printemps.core.member.MemberRepository;
import printemps.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService{
  
  private final MemberRepository memberRepository = new MemoryMemberRepository();
  private final DiscoutPolicy discoutPolicy = new FixDiscountPolicy();

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    
    Member member = memberRepository.findById(memberId);
    int discountPrice = discoutPolicy.discout(member, itemPrice);
    
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

}
