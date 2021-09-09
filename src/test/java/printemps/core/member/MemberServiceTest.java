package printemps.core.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import printemps.core.AppConfig;

public class MemberServiceTest {
  
  MemberService memberService;
  
  @BeforeEach
  public void beforeEach() {
    AppConfig appConfig = new AppConfig();
    memberService = appConfig.memberService();
  }
  
  @Test
  void join() {
    // given
    Member member = new Member(1L, "memberA", Grade.VIP);
    
    // when
    memberService.join(member);
    Member finedMember = memberService.findMember(member.getId());
    //Member finedMember = memberService.findMember(2L);
    
    // then
    Assertions.assertThat(member).isEqualTo(finedMember);
  }
  
}
