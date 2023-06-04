# 8장. 객체 만들기

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
통화?
```

### 하위 클래스 참조 줄이기

- 하위 클래스에 대한 참조를 제거하고 싶지만 한 번에 제거하면 tdd를 효과적으로 보여주기 힘듦
- 참조 줄이기

### 1. 생성자 수정

```java
@Test
@DisplayName("달러에 수를 곱한 결과를 얻어야 함")
void testMultiplication() throws Exception {
    //given
    Dollar five = Money.dollar(5);

    //when
    Dollar product1 = five.times(2);
    Dollar product2 = five.times(3);

    //then
    assertThat(product1).isEqualTo(new Dollar(10));
    assertThat(product2).isEqualTo(new Dollar(15));
}
```

### 2. 팩터리 메서드 패턴 코드 수정

```java
//money
public static Dollar dollar(int amount) {
    return new Dollar(amount);
}
```

### 3. 타입 수정

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
    assertThat(product1).isEqualTo(new Dollar(10));
    assertThat(product2).isEqualTo(new Dollar(15));
}
```

### 4. Money 추상 클래스로 수정

```java
public abstract class Money {
    public abstract Money times(int multiplier);
}
```

### 5. 팩터리 메서드 리턴 타입 수정

```java
//money
public static Money dollar(int amount) {
    return new Dollar(amount);
}
```

### 6. 나머지 dollar 생성자 전부 제거

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
통화?
```

- 동일한 메서드(times) 중복 제거
- 메서드 선언부 상위 클래스로 옮김
- 팩터리 메서드 패턴 도임
- 몇 테스트가 불필요한 것을 인식했지만 그냥 둠

