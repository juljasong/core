package printemps.core.order;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import printemps.core.discount.DiscountPolicy;
import printemps.core.member.Member;
import printemps.core.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
  
  private final MemberRepository memberRepository;
  private final DiscountPolicy discoutPolicy; // 인터페이스에 의존 -> NPE
  //private final DiscoutPolicy discoutPolicy = new FixDiscountPolicy();
  //private final DiscoutPolicy discoutPolicy = new RateDiscountPolicy();

  /*
  @Autowired
  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
    //super();
    this.memberRepository = memberRepository;
    this.discoutPolicy = discountPolicy;
  }
  */

  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    
    Member member = memberRepository.findById(memberId);
    int discountPrice = discoutPolicy.discout(member, itemPrice);
    
    return new Order(memberId, itemName, itemPrice, discountPrice);
  }

  public MemberRepository getMemberRepository() {
    return memberRepository;
  }

}
