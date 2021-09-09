# 20210909_ Initialize Project
using https://start.spring.io/
- Gradle Project
- 2.5.4

# 20210909_ Add Member, Order, Discount 

@Test
Assertions.assertThat(비교값).isEqualTo(예상값);

# 20210909_ Add AppConfig for DIP(Dependency Inversion Principle)
AppConfig ?
- 애플리케이션의 실제 동작에 필요한 구현 객체 생성
- 생성한 객체 인스턴스의 참조(레퍼런스)를 생성자를 통해 주입(연결) => DI(Dependency Injection)

```
@Test
@BeforeEach // 테스트 실행 전 무조건 실행할 코드
  public void beforeEach() { ... } 
```