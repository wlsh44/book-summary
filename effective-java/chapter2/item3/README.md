# private 생성자나 열거 타입으로 싱글턴임을 보증하라

**싱글턴이란**
- 인스턴스를 오직 하나만 생성할 수 있는 클래스

## 기본적인 만드는 방식

```java
public class Elvis {
    public static final Elvis INSTANCE = new Elvis();
    private Elvis() {}
}
```

- 생성자를 private로 호출하면서 Elvis 클래스가 초기화 될 때 딱 한 번만 생성자가 호출됨
- 자바 리플렉션을 사용하면 접근을 할 수는 있음

장점
- 싱글턴임이 명백히 코드에서 드러남
- 간결함

## 정적 팩터리를 이용한 싱글턴

```java
public class Elvis {
    private static final Elvis INSTANCE = new Elvis();
    private Elvis() {}
    
    public static Elvis getInstance() {
        return INSTANCE;
    }
}
```

장점
- API의 변경 없이 싱글턴이 아니게 변경할 수 있음
- 제네릭 싱글턴 팩터리로 만들 수 있음
- 정적 팩터리의 메서드 참조를 공급자(supplier)로 사용할 수 있음
  - Elvis::getInstance -> Supplier<Elvis>

두 방식 모두 직렬화하려면 Serializable을 구현하는 것으로 부족함
- 모든 인스턴스를 일시적(transient) 선안하고 readResolve 메서드를 제공해야 함
- 이렇게 하지 않으면 역직렬화할 때 다른 인스턴스가 만들어짐

```java
private Object readResolve() {
    //'진짜' Elvis를 반환하고, 가짜 Elvis는 gc에 맡김
    return INSTANCE;
}
```

## enum을 이용한 방법

```java
public enum Elvis {
    INSTANCE
}
```

- 간결하고 추가 노력없이 직렬화 할 수 있음
- 리플렉션 공격도 막음
- 대부분의 상황에서 제일 좋은 방법