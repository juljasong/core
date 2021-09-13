package printemps.core.member;

public class MemberServiceImpl implements MemberService {
  
  private MemberRepository memberRepository;

  public MemberServiceImpl(MemberRepository memberRepository) {
    // super();
    this.memberRepository = memberRepository;
  }

  @Override
  public void join(Member member) {
    memberRepository.save(member);
  }

  @Override
  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }
  
  // 테스트용
  public MemberRepository getMemberRepository() {
    return memberRepository;
  }

}
