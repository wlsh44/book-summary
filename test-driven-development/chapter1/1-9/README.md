# 9장. 우리가 사는 시간

**할 일 목록**

```text
$5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 * 2 = $10
✅ amount를 private로 만들기
✅ Dollar의 부작용?
Money 반올림?
✅ equals()
hashCode()
Equal null
Equal object
✅ 5CHF * 2 = 100CHF
Dollar/Franc 중복 ⬅
✅ 공용 equals
공용 times
✅ Franc와 Dollar 비교하기
통화? ⬅
testFrancMultiplication 제거 ⬅
```

### 1. 통화 테스트 추가

```java
@Test
@DisplayName("통화 테스트")
void testCurrency() throws Exception {
    //given
    Money dollar = Money.dollar(1);
    Money franc = Money.franc(1);

    //when
    String dollarCurrency = dollar.currency();
    String francCurrency = franc.currency();

    //then
    assertThat(dollarCurrency).isEqualTo("USD");
    assertThat(francCurrency).isEqualTo("CHF");
}
```

### 2. 코드 구현

```java
//money
public abstract String currency();

//franc
@Override
public String currency() {
    return "USD";
}

//dollar
@Override
public String currency() {
    return "USD";
}
```

### 2. 코드 수정

```java
//franc
public class Franc extends Money {

    private String currency;

    public Franc(int amount) {
        this.amount = amount;
        this.currency = "CHF";
    }

    @Override
    public String currency() {
        return currency;
    }
}

//dollar
public class Dollar extends Money {

    private String currency;

    public Dollar(int amount) {
        this.amount = amount;
        this.currency = "USD";
    }
    
    @Override
    public String currency() {
        return currency;
    }
}
```

### 3. currency 상위 클래스에 이동

```java
//money
protected String currency;

public String currency() {
    return currency;
}
```

### 4. 정적 팩터리 수정

```java
public Franc(int amount, String currency) {
    this.amount = amount;
    this.currency = "CHF";
}

public static Franc franc(int amount) {
    return new Franc(amount, null);
}

public Franc times(int multiplier) {
    return new Franc(amount * multiplier, null);
}
```

### 5. times 팩터리 메서드 호출하도록 변경

- 목적이 아닌 경우 나중에 수정을 하는 것이 원칙이지만 짧은 중단의 경우 수정

```java
public Franc times(int multiplier) {
    return Money.franc(amount * multiplier, null);
}
```

### 6. 인자로 통화 전달

```java
public Franc(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
}

public static Franc franc(int amount) {
    return new Franc(amount, "CHF");
}
```

- ~~이렇게 작은 단계를 밟으면서 일해야 한다~~
- **이런 식으로 일할 수도 있어야 한다**

### 7. Dollar 수정

```java
//money
public static Money dollar(int amount) {
    return new Dollar(amount);
}
```

### 8. 생성자 올리기

```java
//money
public Money(int amount, String currency) {
    this.amount = amount;
    this.currency = currency;
}

//dollar
public Dollar(int amount, String currency) {
    super(amount, currency);
}

//franc
public Franc(int amount, String currency) {
    super(amount, currency);
}
```

```java
@Test
@DisplayName("달러에 수를 곱한 결과를 얻어야 함")
void testMultiplication() throws Exception {
    //given
    Money five = Money.dollar(5);

    //when
    Dollar product1 = five.times(2);
    Dollar product2 = five.times(3);

    //then
    assertThat(product1).isEqualTo(Money.dollar(10));
    assertThat(product2).isEqualTo(Money.dollar(15));
}
```

```text
$5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 * 2 = $10
✅ amount를 private로 만들기
✅ Dollar의 부작용?
Money 반올림?
✅ equals()
hashCode()
Equal null
Equal object
✅ 5CHF * 2 = 100CHF
✅ Dollar/Franc 중복
✅ 공용 equals
공용 times
✅ 통화?
testFrancMultiplication 제거 ⬅
```

- 큰 설계 전에 작은 작업 수행
- times가 팩터리 메서드를 사용하도록 만들기 위해 리팩터링 진행
- 동일 생성자 상위 클래스로 올림

