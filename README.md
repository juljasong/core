# 20210909_ Initialize Project
using https://start.spring.io/
- Gradle Project
- 2.5.4

# 20210909_ Add Member, Order, Discount 

@Test
Assertions.assertThat(�񱳰�).isEqualTo(����);

# 20210909_ Add AppConfig for DIP(Dependency Inversion Principle)
AppConfig ?
- ���ø����̼��� ���� ���ۿ� �ʿ��� ���� ��ü ����
- ������ ��ü �ν��Ͻ��� ����(���۷���)�� �����ڸ� ���� ����(����) => DI(Dependency Injection)

```
@Test
@BeforeEach // �׽�Ʈ ���� �� ������ ������ �ڵ�
  public void beforeEach() { ... } 
```