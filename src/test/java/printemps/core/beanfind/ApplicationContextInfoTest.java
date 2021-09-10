package printemps.core.beanfind;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import printemps.core.AppConfig;

public class ApplicationContextInfoTest {
  
  AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

  @Test
  @DisplayName("��� �� ���")
  void findAllBean() {
    String[] beanDefinitionNames =  ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      Object bean = ac.getBean(beanDefinitionName);
      System.out.println("name = " + beanDefinitionName + "object : " + bean );
    }
  }
  
  @Test
  @DisplayName("���ø����̼� �� ���")
  void findApplicationBean() {
    String[] beanDefinitionNames =  ac.getBeanDefinitionNames();
    for (String beanDefinitionName : beanDefinitionNames) {
      BeanDefinition beanDefinition =  ac.getBeanDefinition(beanDefinitionName);
      
      //if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) { // ���� ����� ���ø����̼� ��
      if (beanDefinition.getRole() == BeanDefinition.ROLE_INFRASTRUCTURE) { // �������� ���ο��� ����ϴ� ��
        Object bean = ac.getBean(beanDefinitionName);
        System.out.println("name = " + beanDefinitionName + "object : " + bean );
      }
    }
  }
  
}
