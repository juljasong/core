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

# 20210913_ Add /test/SingletonService.java
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

### 중복 등록과 충돌
1. 자동 빈 등록 vs 자동 빈 등록
2. 수동 빈 등록 vs 자동 빈 등록 : 스프링은 수동 빈이 자동 빈을 오버라이딩할 수 있는 것이 기본.
   부트에서는 오버라이딩 불가능한 것이 기본. 부트에서 수동 빈이 자동 빈을 오버라이딩 하기 위해서는 기본값 설정 변경 필요.

application.properties
````
spring.main.allow-bean-definition-overriding=true
````

### 의존 관계 주입 방법
  - 생성자 주입 : 생성자 호출 시점에 1번만 호출 되는 것이 보장됨
````java
@Component
public class MemberServiceImpl implements MemberService {
  @Autowired
  public MemberServiceImpl(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
  }
 }
 ````
  - 수정자 주입(setter 주입)
  - 필드 주입(권장 X) : 외부 변경 불가능하여 테스트하기 어려움. 테스트 코드에서는 사용 O.
````java
@Autowired private MemberRepository
````
  - 일반 메서드 주입 : Spring Container가 관리하는 Spring Bean이어야 동작함.

### 옵션
- @Autowired(required=false) : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
- org.springframework.lang.@Nullable : 자동 주입할 대상이 없으면 null이 입력된다.
- Optional<> : 자동 주입할 대상이 없으면 Optional.empty 가 입력된다.

````java
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
````

### 생성자 주입
- 불변, 누락 방지
- final 키워드 사용 가능
- 프레임 워크 의존 X 순수 자바 언어 특성의 특징을 잘 살림

# 롬복
File - Settings - Compiler - Annotation Processors - [Check] Enable annotiation processing 
- @Getter 
- @Setter
- @ToString
- @RequiredArgsConstructor : final이 붙은 변수를 파라미터로 받는 생성자 자동 완성. 의존관계 추가할 때 편함

### 조회하는 빈이 2개 이상일 때
- @Autowired : Type으로 매칭 시도하여, 여러 빈이 존재하면, 필드 이름, 파라미터 이름으로 빈 이름 추가 매칭
=> 문제 발생 시 해결방법
  - @Autowired 필드명 매칭
````java
  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;

  @Autowired 
  public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy rateDiscountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = rateDiscountPolicy;
  }
````
  - @Qualifier("name") -> @Qualifier 끼리 매칭 (-> 빈 이름 매칭) : 주입받을 때 모든 코드에 기재해주어야 함
  - @Primary 사용 : @Autowired 시 여러 빈이 매칭되면 해당 @Primary가 우선권을 가짐
    - 우선순위 : @Qualifier > @Primary

### 어노테이션 직접 만들기
~annotation.java
````java
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy { }
````
RateDiscountPolicy.java
````java
@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy{ }
````
OrderServiceImpl.java
````java
@Component
//@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
  
  private final MemberRepository memberRepository;
  private final DiscountPolicy discountPolicy;
  
  @Autowired
  public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
  }
}
````

### 조회한 빈이 모두 필요할 때, List, Map
- test/printemps/core/autowired/AllBeanTest.java

### 자동, 수동의 올바른 실무 운영 기준
- 자동 기능을 기본으로 사용
- 수동 빈 등록이 필요한 경우
  - 업무 로직(핵심 비지니스 로직 등)이 아닌 직접 등록하는 기술 지원(기술적인 문제나 공통 관심사(AOP) 처리) 로직에서 사용하는 것을 권장 (*스프링, 스프링 부트가 자동으로 등록하는 빈들은 예외)
    - 업무 로직에 비해 그 수가 적고, 보통 애플리케이션 전반에 걸쳐 광범위하게 영향을 미치기 때문에 적용이 잘 되고 있는지 조차 파악하기 어려운 경우가 많음.
  - 업무 로직 중 다형성을 적극 활용할 때
    - DiscountPolicy와 같은 경우에는 수동 빈으로 등록하거나, 자동인 경우 특정 패키지에 같이 묶어두는 것이 좋다(한 눈에 볼 수 있음)
```java
@Configuration
public class DiscountPolicyConfig {
  @Bean
  public DiscountPolicy rateDiscountPolicy() {
    return new RateDiscountPolicy();
  }
  @Bean
  public DiscountPolicy fixDiscountPolicy() {
    return new FixDiscountPolicy();
  }
}
```

# 20210915_ 빈 생명주기

### 스프링 빈 이벤트 라이프 사이클

- 스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸 전 콜백 -> 스프링 종료
  - 초기화 콜백 : 빈이 생성되고, 빈의 의존관계 주입이 완료된 후 호출
  - 소멸 전 콜백 : 빈이 소멸되기 직전 호출

### ~~1. 인터페이스 InitializingBean, DisposableBean~~
- 단점 : 스프링 전용 인터페이스(해당 코드가 스프링 전용 인터페이스에 의존)
- 초기화, 소멸 메서드의 이름 변경 불가
- 외부 라이브러리 적용 불가
````java
public class NetworkClient implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient() { System.out.println("생성자 호출, url = " + url); }

    public void setUrl(String url) { this.url = url; }
    
    public void connect() { System.out.println("connect: " + url); }

    public void call(String message) { System.out.println("Call: " + url + " message = " + message); }
  
    public void disconnect() { System.out.println("close: " + url); }

    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 연결 메시지");
    }

    @Override
    public void destroy() throws Exception { disconnect(); }
}
````

### 2. 빈 등록 초기화, 소멸 메서드
- 설정 정보에 @Bean(initMethod = "init", destroyMethod = "close") 와 같은 형태로 지정
- 메서드 이름을 자율옵게 줄 수 있음
- 스프링 빈이 스프링 코드에 의존 X
- 코드가 아닌 설정 정보를 이용하여 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있음 

Config
````java
        //@Bean(initMethod = "init", destroyMethod = "close")
        @Bean(initMethod = "init")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://url.com"); // 객체 생성 후 set
            return networkClient;
        }
````
- 종료 메서드 추론(destroyMethod)
  - 라이브러리의 종료 메서드의 이름은 대부분 close, shutdown 
  - @Bean의 destroyMethod의 기본값은 (inferred)
  - close, shutdown이라는 이름의 메서드를 자동으로 호출해줌

### 3. 어노테이션 @PostConstruct, @PreDestroy
- javax.annotation -> 스프링이 아니더라도 java에서 지원해줌
- 최신 스프링에서 가장 권장하는 방법
- 컴포넌트 스캔과 잘 어울림
  - 외부 라이브러리에는 적용하지 못함. 외부 라이브러리를 초기화, 종료해야하는 경우에는 2번 사용

# Bean Scope
- 싱글톤 : 스프링 기본 스코프. 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프
- 프로토타입 : 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 매우 짧은 범위의 스코프
- 웹 관련 스코프
  - request : 웹 요청이 들어오고 나갈 때 까지 유지되는 스코프
  - session : 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프
  - application : 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프

### 프로토타입 스코프
- 항상 새로운 인스턴스를 생성하여 반환
- 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입 및 초기화 까지만 관여
- 종료 메서드 호출 X
- => 프로토타입 빈을 조회한 클라이언트가 관리해야 함. 종료 메서드 호출도 클라이언트가 직접 해야 함.

### 싱글톤 빈과 함께 사용시 문제점
- 싱글톤 빈이 프로토타입 빈을 사용하게 되는 경우, 싱글톤 빈은 생성 시점에만 의존 관계 주입을 받기 때문에 새로 생성된 프로토타입 빈이 싱글톤 빈과 함께 계속 유지된다.

#### → Provider로 문제 해결
- 싱글톤 빈과 프로토타입 빈 함께 사용 시, 항상 새로운 프로토타입 빈 생성하는 방법
  1. 싱글톤 빈이 프로토타입을 생성할 때 마다 스프링 컨테이너에 새로 요청
  2. ObjectProvider/Factory 사용 -> DL(Dependency Lookup). 스프링 컨테이너를 통해 해당 빈 찾아 반환
````java
public class SingletonWithPrototypeTest {
    
    @Test
    void singletonClientUsePrototype() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);
        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(1);

    }

    // default: singleton
    static class ClientBean {

        @Autowired private ObjectProvider<PrototypeBean> prototypeBeanProvider;
        
        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            return prototypeBean.getCount();
        }
    }
}
````
  3. JSR-330 Provider
    - gradle implementation 'javax.inject:javax.inject:1' gradle 추가 필수 (외부 라이브러리 필요)
    - 자바 표준이므롤 스프링이 아닌 다른 컨테이너에서도 사용할 수 있음.
````java
package javax.inject;
public interface Provider<T> {
    T get();
}

//implementation 'javax.inject:javax.inject:1' gradle 추가 필수
  
@Autowired private Provider<PrototypeBean> provider;
public int logic() {
    PrototypeBean prototypeBean = provider.get();
    prototypeBean.addCount();
    int count = prototypeBean.getCount();
    return count;
}
````

# 웹 스코프
- 웹 환경에서만 동작
- 스프링이 해당 스코프의 종료시점까지 관리. 종료 메서드가 호출됨
  - request : HTTP 요청 하나가 들어오고 나갈 때 까지 유지되는 스코프. 각각의 HTTP 요청 마다 별도의 빈 인스턴스가 생성되고 관리됨
  - session : HTTP Session과 동일한 생명 주기를 가지는 스코프
  - application : 서블릿 컨텍스트와 동일한 생명 주기를 가지는 스코프
  - websocket : 웹 소켓과 동일한 생명 주기를 가지는 스코프

````
//web 라이브러리 추가 : 내장 톰캣 서버 활용하여 웹 서버와 스프링 함께 실행시킴
implementation 'org.springframework.boot:spring-boot-starter-web'
````

### request 스코프
- 동시에 여러 HTTP 요청이 오면 정확히 어떤 요청이 남긴 로그인지 구분하기 어려움 -> 이런 경우 사용할 수 있음
- [UUID][requestURL]{message} -> UUID를 이용하여 HTTP 요청 구분