# 인스턴스화를 막으려거든 private 생성자를 사용하라

정적 멤버만 담은 유틸리티 클래스같이 인스턴스로 만들어 쓰려고 설계한 클래스가 아닌 경우
- 인스턴스화를 막아야 함

**추상클래스**
- 사실상 하위 클래스를 만들면 되기 때문에 막을 수 없음

**private 생성자**
- 인스턴스화 막을 수 있음
- 상속도 막을 수 있음

```java
public class UtilityClass {
    
    private UtilityClass() {
        //내부 호출을 막기 위한 예외
        throw new AssertionError();
    } 
}
```
