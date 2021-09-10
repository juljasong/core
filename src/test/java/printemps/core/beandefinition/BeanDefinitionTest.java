package printemps.core.beandefinition;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import printemps.core.AppConfig;

public class BeanDefinitionTest {
  
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
  
  @Test
  @DisplayName("�� ���� ��Ÿ���� Ȯ��")
  void findApplicationBean() {
    String[] beanDefinitionNames =  ac.getBeanDefinitionNames();
    
    for(String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName); // AnnotationConfigApplicationContext에만 정의되어 있음
      
      if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
        System.out.println("beanDefinitionName = " + beanDefinitionName + "beanDefinition = " + beanDefinition);
      }
    }
  }

}
