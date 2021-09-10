package printemps.core.beanfind;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import printemps.core.member.MemberRepository;
import printemps.core.member.MemoryMemberRepository;

public class ApplicationContextSameBeanFindTest {

  AnnotationConfigApplicationContext ac =
      new AnnotationConfigApplicationContext(SameBeanConfig.class);

  @Test
  @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 중복 오류 발생") // NoUniqueBeanDefinitionException
  void findBeanByTypeDuplicate() {
    // MemberRepository memberRepository = ac.getBean(MemberRepository.class);
    Assertions.assertThrows(NoUniqueBeanDefinitionException.class,
        () -> ac.getBean(MemberRepository.class));
  }

  @Test
  @DisplayName("타입으로 조회 시 같은 타입이 둘 이상 있으면, 빈 이름 지정하면 됨")
  void findBeanByName() {
    org.assertj.core.api.Assertions
        .assertThat(ac.getBean("memberRepository1", MemberRepository.class))
        .isInstanceOf(MemberRepository.class);
  }
  
  @Test
  @DisplayName("특정 타입 모두 조회")
  void findAllBeanByType() {
    Map<String, MemberRepository> beansOfType = ac.getBeansOfType(MemberRepository.class);
    for(String key : beansOfType.keySet()) {
      System.out.println("key = " + key + "value = " + beansOfType.get(key));
    }
    System.out.println("beansOfType" + beansOfType);
    org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
  }
  
  
  @Configuration
  static class SameBeanConfig { // static ->클래스 내에서만 쓰겠다

    @Bean
    public MemberRepository memberRepository1() {
      return new MemoryMemberRepository();
    }

    @Bean
    public MemberRepository memberRepository2() {
      return new MemoryMemberRepository();
    }

  }

}
