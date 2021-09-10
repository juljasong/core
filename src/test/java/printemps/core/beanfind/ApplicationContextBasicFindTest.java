package printemps.core.beanfind;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import printemps.core.AppConfig;
import printemps.core.member.MemberService;
import printemps.core.member.MemberServiceImpl;

public class ApplicationContextBasicFindTest {

  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("�� �̸����� ��ȸ")
  void findBeanByName() {
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("�̸� ���� Ÿ������ ��ȸ")
  void findBeanByType() {
    MemberService memberService = ac.getBean(MemberService.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("��ü Ÿ������ ��ȸ")
  void findBeanByName2() {
    MemberService memberService = ac.getBean(MemberServiceImpl.class);
    Assertions.assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
  }

  @Test
  @DisplayName("�� �̸����� ��ȸX")
  void findBeanByNameX() {
    // MemberService memberService = ac.getBean("xxx", MemberService.class);
    org.junit.jupiter.api.Assertions.assertThrows(NoSuchBeanDefinitionException.class,
        () -> ac.getBean("xxx", MemberService.class)); // �ش� ������ �߻��ؾ߸� OK
  }

}
