package printemps.core.singleton;

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
