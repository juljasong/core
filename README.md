# 20210909_ Initialize Project
using https://start.spring.io/
- Gradle Project
- 2.5.4

# 20210909_ Add Member, Order, Discount 

```java
@Test
Assertions.assertThat(비교값).isEqualTo(예상값);
```

# 20210909_ Add AppConfig for DIP(Dependency Inversion Principle)
AppConfig ?
- 애플리케이션의 실제 동작에 필요한 구현 객체 생성
- 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해 주입(연결) => DI(Dependency Injection)

```java
@Test
@BeforeEach // 테스트 실행 전 무조건 실행할 코드
  public void beforeEach() { ... } 
```

# 20210910_ Spring
- IoC(Inversion of Control)
- Framework vs Library
- DI

BeanDefinition.ROLE_APPLICATION : 직접 등록한 어플리케이션 빈
BeanDefinition.ROLE_INFRASTRUCTURE : 스프링이 내부에서 사용하는 빈

### [MOD] AppConfig.java, ~App.java

- AppConfig.java : Bean  등록

```java
@Configuration 
public class AppConfig {
  @Bean
  public MemberService memberService() {
    return new MemberServiceImpl(memberRepository());
  }
}
```
- ~App.java : 빈 조회

ac.getBean("Bean_name", TYPE)
ac.getBean(TYPE)

```java
  public static void main(String[] args) {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService = ac.getBean("memberService", MemberService.class);
    OrderService orderService = ac.getBean("orderService", OrderService.class);
  }
```

### BeanFactory? ApplicationContext

BeanFactory

- 스프링 컨테이너 최상위 인터페이스
- 스프링 빈 관리 및 조회
- .getBean() 제공

ApplicationContext

- BeanFactory 기능 모두 상속받아 제공
- 어플리케이션 개발 시 필요한 부가기능 제공

### XML 설정
: 최근에는 잘 사용하지 않음. 컴파일 없이 빈 설정 정보 변경할 수 있어 이상적이기도......

### BeanDefinition 정보

BeanClassName
factoryBeanName
factoryMethodName
Scope
lazyInit
InitMethodName
DestroyMethodName
Constructor arguments, Properties

# 20210913_ [ADD] /test/SingletonService.java
### Singleton
- 생성자가 여러 차례 호출되더라도 실제로 생성되는 객체는 하나이고, 최초 생성 이후에 호출된 생성자는 최초의 생성자가 생성한 객체를 리턴

````java
public class SingletonService {

    private static final SingletonService instance = new SingletonService();

    // static으로 선언하여 객체를 1개만 생성
    public static SingletonService getInstance() {
        return instance;
    }

    // 생성자를 private으로 선언하여 외부에서 new 키워드 사용한 객체 생성 방지
    private SingletonService() {}

    private void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }
    
}
````

### Singleton Container
- 스프링 컨테이너는 싱글턴 패턴 적용하지 않아도 객체 인스턴스를 싱글톤으로 관리함
- 싱글톤 레지스트리 : 싱글톤 객체를 생성하고 관리하는 기능
- 스프링 컨테이너가 제공하는 싱글턴 패턴의 장점
  - 싱글톤 패턴을 위한 지저분한 코드 필요 X
  - DIP, OCP, 테스트, private 생성자로 부터 자유롭게 싱글톤 이용 가능



