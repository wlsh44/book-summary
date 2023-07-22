# 생성자 대신 정적 팩터리 메서드를 고려하라

### TL;DR

**정적 팩터리 장점 5가지**

- 이름을 가질 수 있다
- 호출될 때마다 인스턴스를 새로 생성하지는 않아도 된다.
- 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.
- 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.
- 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

**정적 팩터리 단점 2가지**

- 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.
  - 상속보다 컴포지션을, 객체를 불변 타입으로 만들기 위해서는 장점이 될 수도 있다.
- 정적 팩터리 메서드는 프로그래머가 찾기 어럽다.

**핵심 정리**

정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는 것이 좋다. 그렇다고 하더라도 정적 팩터리를 사용하는게 유리한 경우가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있다면 고치자.

## 정적 팩터리 메서드란?

- 클래스 생성자와 별도로 static 메서드를 이용해 인스턴스를 반환하는 방식

```java
public static Boolean valueOf(boolean b) {
  return b ? Boolean.TRUE : Boolean.FALSE;
}
```

> 디자인 패턴의 팩터리 메서드와 다르다.
> 차이점은 팩터리 메서드는 다형성과 더 연관이 되는 디자인 패턴

## 장점

### 1. 이름을 가질 수 있다.

```java
//생성자
BigInteger(int, int, Random)

//정적 팩터리 메서드
BigInteger.probablePrime()
```
- 첫 번째 생성자보다 두 번째 정적 팩터리 메서드가 '값이 소수인 BigInteger를 반환한다'는 의미를 더 잘 설명함
- 이름을 부여하는 것 자체가 클린 코드를 향한 길
- 하나의 시그니처로는 생성자를 하나만 만들 수 있음
  - 특히 매개 변수가 같은 경우 순서를 다르게 한 생성자를 만드는건 좋지 않은 발상

### 2. 호출될 때마다 인스턴스를 새로 생성하지 않아도 된다.

- 불변 클래스같은 경우 인스턴스를 미리 만들어놓거나, 캐싱을 이용하여 불필요한 객체 생성을 피할 수 있음
- Boolean.valueOf(boolean)이 대표적인 예시
- 플라이웨이트 패턴과 유사
- 언제 어느 인스턴스를 살아 있게 할지를 통제할 수 있음
  - 인스턴스 통제(instance-controlled)클래스
  - 싱글턴 패턴, 불변 인스턴스를 만드는 기반이 됨

### 3. 반환 타입의 하위 타입 객체를 반환할 수 있는 능력이 있다.

- API를 만들 때, 구현 클래스를 공개하지 않고도 객체를 반환할 수 있음
  - 객체지향의 추상화를 가능하게 함
  - 인터페이스 기반 프레임워크 핵심 기술
 
```java
Collections<> c = Collections.unmodifiableCollection(collections);
```
- unmodifiableCollection의 구현체를 모르는 상태로 사용할 수 있음

> 자바 8에서 정적 메서드를 가질 수 없다는 제한이 풀렸기 때문에 인스턴스화 불가 동반 클래스를 둘 이유가 별로 없다.
> 동반 클래스에 두었던 public 정적 멤버들 상당수를 그냥 인터페이스 자체에 두면 되는 것이다.
>
> 이게 무슨 소리지..?

```java
//인터페이스 정적 메서드 예시
public interface StaticInterface {
    static void staticHello() {
        System.out.println("static Hello");
    }
    
    void publicHello();
}
```

### 4. 입력 매개변수에 따라 매번 다른 클래스의 객체를 반환할 수 있다.

- 반환 타입의 하위 타입이기만 하면 어떤 클래스의 객체를 반환하든 상관 없음
  - **개인적으로 이 방법보다는 팩터리 패턴을 사용해야 된다고 생각**

```java
public static <E extends Enum<E>> EnumSet<E> noneOf(Class<E> elementType) {
    Enum<?>[] universe = getUniverse(elementType);
    if (universe == null)
        throw new ClassCastException(elementType + " not an enum");

    if (universe.length <= 64)
        return new RegularEnumSet<>(elementType, universe);
    else
        return new JumboEnumSet<>(elementType, universe);
}
```
- 매개변수에 따라 RegularEnumSet또는 JumboEnumSet을 리턴하는데, 해당 코드는 EnumSet이라는 추상 클래스가 구현 클래스인 RegularEnumSet과 JumbEnumSet에 의존하게 된다고 생각
- 클라이언트가 누구냐에 따라 다른건가..?

### 5. 정적 팩터리 메서드를 작성하는 시점에는 반환할 객체의 클래스가 존재하지 않아도 된다.

- 서비스 제공자 프레임워크(service provider framework)를 만드는 근간이 됨
  - 서비스 인터페이스(service interface): 구현체의 동작을 정의
  - 제공자 등록 API(provider registration API): 제공자가 구현체를 등록할 때 사용
  - 서비스 접근 API(service access API): 클라이언트가 인스턴스를 얻을 때 사용
    - 이 부분이 유연한 정적 팩터리의 실체
  - 서비스 제공자 인터페이스(service provider interface): 서비스 인터페이스의 인스턴스를 생성하는 팩터리 객체
- JDBC가 대표적인 예시
  - Connection - 서비스 인터페이스
  - DriverManager.registerDriver - 제공자 등록 API
  - Driver.getConnection - 서비스 접근 API
  - Driver - 서비스 제공자 인터페이스

## 단점 

### 1. 상속을 하려면 public이나 protected 생성자가 필요하니 정적 팩터리 메서드만 제공하면 하위 클래스를 만들 수 없다.

```java
public class A {
  private A() {...}
  public static A createA() {...}
}

public class B extends A {
  public B() {
    super(); // 예외 발생
  }
```
- 이런 문제가 발생함

=> 상속보다 컴포지션을 사용하도록 유도할 수 있기 때문에 오히려 장점이 될 수도 있음

### 2. 정적 팩터리 메서드는 프로그래머가 찾기 어렵다.

- 생성자와 달리 정적 팩터리 메서드인지, 그냥 메서드인지 알기 힘듬
- 그래서 API 문서를 잘 써야 한다는 단점이 있음
- 또는 알려진 규약의 이름을 사용
  - from: 매개변수 하나를 받아서 해당 타입의 인스턴스를 반환하는 형변환 메서드
    - `Date d = Date.from(instant);`
  - of: 여러 매개변수를 받아 적합한 타입의 인스턴스를 반환하는 집계 메서드
    - `Set<Rank> faceCards = EnumSet.of(JACK, QUEEN, KING);`
  - valueOf: from과 of의 더 자세한 버전
    - `BigInteger prime = BigInteger.valueOf(Integer.MAX_VALUE);`
  - instance, getInstance: 인스턴스를 반환하지만 같은 인스턴스임을 보장하지는 않음
    - `StackWalker luke = StackWalker.getInstance();`
  - create 혹은 newInstance: 매번 새로운 인스턴스를 생성해 반환함을 보장
    - `Object newArray = Array.newInstance(classObject, arrayLen);`
  - getType: getInstance와 같음, 생성 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 사용, Type은 팩터리 메서드가 반환할 객체의 타입
    - `FileStore fs = Files.getFileStore(path);`
  - newType: newInstance와 같음, 생성 클래스가 아닌 다른 클래스에 팩터리 메서드를 정의할 때 사용, Type은 팩터리 메서드가 반환할 객체의 타입
    - `BufferedReader br = Files.newBufferedReader(path);`
  - type: getType과 newType의 간결한 버전
    - List<Complaint> litany = Collections.list(legacyLitancy);

