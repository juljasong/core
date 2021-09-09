package printemps.core.order;

import printemps.core.discount.DiscountPolicy;
import printemps.core.member.Member;
import printemps.core.member.MemberRepository;

public class OrderServiceImpl implements OrderService{
  
  private MemberRepository memberRepository;
  private DiscountPolicy discoutPolicy; // 인터페이스에 의존 -> NPE
  //private final DiscoutPolicy discoutPolicy = new FixDiscountPolicy();
  //private final DiscoutPolicy discoutPolicy = new RateDiscountPolicy();
  
  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discoutPolicy) {
    //super();
    this.memberRepository = memberRepository;
    this.discoutPolicy = discoutPolicy;
  }

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    
    Member member = memberRepository.findById(memberId);
    int discountPrice = discoutPolicy.discout(member, itemPrice);
    
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

}
