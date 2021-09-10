# 20210909_ Initialize Project
using https://start.spring.io/
- Gradle Project
- 2.5.4

# 20210909_ Add Member, Order, Discount 

```java
@Test
Assertions.assertThat(�񱳰�).isEqualTo(����);
```

# 20210909_ Add AppConfig for DIP(Dependency Inversion Principle)
AppConfig ?
- ���ø����̼��� ���� ���ۿ� �ʿ��� ���� ��ü ����
- ������ ��ü �ν��Ͻ��� ����(���۷���)�� �����ڸ� ���� ����(����) => DI(Dependency Injection)

```java
@Test
@BeforeEach // �׽�Ʈ ���� �� ������ ������ �ڵ�
  public void beforeEach() { ... } 
```

#20210910_ Spring
- IoC(Inversion of Control)
- Framework vs Library
- DI

BeanDefinition.ROLE_APPLICATION : ���� ����� ���ø����̼� ��
BeanDefinition.ROLE_INFRASTRUCTURE : �������� ���ο��� ����ϴ� ��

###[MOD] AppConfig.java, ~App.java

- AppConfig.java : Bean  ���

```java
@Configuration 
public class AppConfig {
  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }
}
```
- ~App.java : �� ��ȸ

ac.getBean("Bean_name", TYPE)
ac.getBean(TYPE)

```java
  public static void main(String[] args) {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    OrderService orderService = ac.getBean("orderService", OrderService.class);
  }
```
