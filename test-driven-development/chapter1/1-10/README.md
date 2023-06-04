# 10장. 흥미로운 시간

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
✅ 통화?
testFrancMultiplication 제거 ⬅
```

### 1. times 정적 팩터리 메서드 부분 생성자로 변경

```java
//dollar
public Dollar times(int multiplier) {
    return new Dollar(amount * multiplier, "USD");
}

//franc
public Franc times(int multiplier) {
    return new Franc(amount * multiplier, "CHF");
}
```

### 2. 파라미터 변경

```java
//dollar
public Dollar times(int multiplier) {
    return new Dollar(amount * multiplier, currency);
}

//franc
public Franc times(int multiplier) {
    return new Franc(amount * multiplier, currency);
}
```

### 3. 설계 변경

- Franc를 가질지, Money를 가질지, Dollar를 가질지 고민
- 테스트가 있으므로 변경하고 잘 동작하는지 확인

```java
//franc
public Franc times(int multiplier) {
    return new Money(amount * multiplier, currency);
}
```

### 3. times 변경

```java
//franc
public Money times(int multiplier) {
    return new Money(amount * multiplier, "USD");
}
```

### 4. money 구체 클래스로 변경

```java
public class Money {
    public Money times(int multiplier) {
        return null;
    }
}
```

### 5. 테스트 후 toString 정의

- toString의 경우 디버깅용으로만 쓸거기 때문에 테스트 코드 작성 안 해도 됨

```java
//franc
@Override
public String toString() {
    return amount + " " + currency;
}
```

### 6. equals 수정하기 위해 times 롤백

- 테스트를 초록 막대인 상태로 돌림

```java
//franc
public Franc times(int multiplier) {
    return new Franc(amount * multiplier, "USD");
}
```

### 7. 동등성 테스트 코드 추가

```java
@Test
@DisplayName("다른 클래스 동등성 테스트")
void testDifferenceClassEquality() throws Exception {
    //given
    Money money = new Money(10, "CHF");
    Franc franc = Money.franc(10);

    //when

    //then
    assertThat(money).isEqualTo(franc);
}
```

### 8. equals 코드 수정

```java
@Override
public boolean equals(Object obj) {
    Money money = (Money) obj;
    return amount == money.amount &&
        currency.equals(money.currency);
}
```

### 9. times 재변경

```java
//franc
public Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
}
```

### 10. dollar 변경

```java
//dollar
public Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
}
```

### 11. 상위 클래스로 이동

```java
//money
public Money times(int multiplier) {
    return new Money(amount * multiplier, currency);
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
✅ Franc와 Dollar 비교하기
공용 times
✅ 통화?
testFrancMultiplication 제거 ⬅
```


