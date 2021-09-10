package printemps.core.beanfind;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import printemps.core.discount.DiscountPolicy;
import printemps.core.discount.FixDiscountPolicy;
import printemps.core.discount.RateDiscountPolicy;

public class ApplicationContextExtendsFindTest {
  
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
  
  @Test
  @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 중복 오류 발생")
  void findBeanByParentTypeDuplicate() {
    //DiscountPolicy discountPolicy = ac.getBean(DiscountPolicy.class);
    Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
  }
  
  @Test
  @DisplayName("부모 타입으로 조회 시, 자식이 둘 이상 있으면, 빈 이름 지정하면 됨")
  void findBeanByParentTypeBeanName() {
    DiscountPolicy ratediscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
    org.assertj.core.api.Assertions.assertThat(ratediscountPolicy).isInstanceOf(RateDiscountPolicy.class);
  }
  
  @Test
  @DisplayName("특정 하위 타입으로 조회")
  void findBeanBySunType() {
    RateDiscountPolicy rateDiscountPolicy = ac.getBean(RateDiscountPolicy.class);
    org.assertj.core.api.Assertions.assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
  }
  
  
  @Test
  @DisplayName("부모 타입으로 모두 조회하기")
  void findAllBeanByParentType() {
    Map<String, DiscountPolicy> beansofType = ac.getBeansOfType(DiscountPolicy.class);
    org.assertj.core.api.Assertions.assertThat(beansofType.size()).isEqualTo(2);
    
    for (String key : beansofType.keySet()) {
      System.out.println("key = " + key + ", value = " + beansofType.get(key));
    }
  }
  
  @Test
  @DisplayName("부모 타입으로 모두 조회 - Object")
  void findAllBeanByObjectType() {
    Map<String, Object> beansofType = ac.getBeansOfType(Object.class);
    for (String key : beansofType.keySet()) {
      System.out.println("key = " + key + ", value = " + beansofType.get(key));
    }
  }
  
  @Configuration
  static class TestConfig {
    
    @Bean
    public DiscountPolicy rateDiscountPolicy() {
      return new RateDiscountPolicy();
    }
    
    @Bean
    public DiscountPolicy fixDiscountPolicy() {
      return new FixDiscountPolicy();
    }
  }

}
