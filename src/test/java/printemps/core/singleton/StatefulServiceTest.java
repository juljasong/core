package printemps.core.singleton;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : A사용자 10000원 주문
        int userAprice = statefulService1.order("userA", 10000);

        // ThreadB : B사용자 20000원 주문
        int userBprice = statefulService2.order("userB", 20000);

        // ThreadA : 사용자A가 주문 금액 조회
        // int price = statefulService1.getPrice();
        System.out.println(userAprice); // 20000?

        //org.assertj.core.api.Assertions.assertThat(price).isNotEqualTo(10000);
        org.assertj.core.api.Assertions.assertThat(userAprice).isEqualTo(10000);
    }

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}