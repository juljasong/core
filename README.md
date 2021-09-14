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
- 주의점
  - 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 무상태(stateless)로 설계해야 함
    - 특정 클라이언트에 의존적인 필드가 있으면 안됨
    - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안됨
    - 가급적 읽기만 가능해야
    - 필드 대신 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용해야 함

# 20210914 Add AutoAppConfig.java
### Auto ComponentScan
- @Bean으로 직접 설정 정보를 작성하지 않기 때문에, 의존 관계 주입을 클래스 안에서 해결해야 함
  - @ComponentScan
  - @Component
  - @Autowired : 자동 주입

````java
@ComponentScan(
        // 탐색 위치 지정
        basePackages = "hello.core.member",
        basePackageClasses = AutoAppConfig.class,
        // 자동 스캔 대상 필터(AppConfig.java 제외)
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
````
### 컴포넌트 스캔 기본 대상
  - @Component : 컴포넌트 스캔에서 사용
  - @Controlller : 스프링 MVC 컨트롤러에서 사용
  - @Service : 스프링 비즈니스 로직에서 사용
  - @Repository : 스프링 데이터 접근 계층에서 사용
  - @Configuration : 스프링 설정 정보에서 사용

### 필터
- includeFilters : 컴포넌트 스캔 대상을 추가로 지정한다.
- excludeFilters : 컴포넌트 스캔에서 제외할 대상을 지정한다.
````java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyExcludeComponent { }
````
````java
@Configuration
    @ComponentScan(excludeFilters = @ComponentScan.Filter(classes = MyExcludeComponent.class))
    static class ComponentFilterAppConfig { }
````
