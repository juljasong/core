package printemps.core.autowired;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.lang.Nullable;
import printemps.core.member.Member;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOptiton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {
        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("member1 : " + member);
        }
        
        @Autowired
        public void setNoBean2(@Nullable Member member) { // 생성자 주입 특정 필드에서만 사용해도 됨
            System.out.println("member2 : " + member);
        }

        @Autowired
        public void setNoBean3(Optional<Member> member) {
            System.out.println("member3 : " + member);
        }
    }
}
