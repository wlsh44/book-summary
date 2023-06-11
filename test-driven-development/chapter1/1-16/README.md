# 16장. 드디어, 추상화

**할 일 목록**

```text
✅ $5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 + $5 = $10
$5 + $5에서 Money 반환하기
✅ Bank.reduce(Money)
✅ Money에 대한 통화 반환을 수행하는 Reduce
✅ Reduce(Bank, String)
Sum.plus ⬅
Expression.times
```

Expression.plus를 끝마치기 위해 Sum.plus를 구현해야 함

## Sum.plus() 테스트 구현

```java
@Test
@DisplayName("sum plus 테스트")
void testSumPlusMoney() throws Exception {
    //given
    Expression fiveBuckets = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);

    //when
    Expression sum = new Sum(fiveBuckets, tenFrancs).plus(fiveBuckets);
    Money result = bank.reduce(sum, "USD");

    //then
    assertThat(result).isEqualTo(Money.dollar(15));
}
```

- fiveBucks와 tenFrancs를 더해서 Sum을 생성할 수도 있지만 명시적으로 Sum을 생성
  - -> 더 직접적으로 Sum을 테스트 하려는 의도를 드러냄

## sum 구현

```java
@Override
public Expression plus(Expression addend) {
    return new Sum(this, addend);
}
```

```text
✅ $5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 + $5 = $10
$5 + $5에서 Money 반환하기
✅ Bank.reduce(Money)
✅ Money에 대한 통화 반환을 수행하는 Reduce
✅ Reduce(Bank, String)
✅ Sum.plus
Expression.times ⬅
```

- TDD로 구현할 때는 테스트 코드의 줄 수와 모델 코드의 줄 수가 거의 비슷한 상태로 끝남

## Expression.times 테스트 구현

```java
@Test
@DisplayName("sum times 테스트")
void testSumTimes() throws Exception {
    //given
    Expression fiveBuckets = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);

    //when
    Expression sum = new Sum(fiveBuckets, tenFrancs).times(2);
    Money result = bank.reduce(sum, "USD");

    //then
    assertThat(result).isEqualTo(Money.dollar(20));
}
```

## 리팩터링

```java
public interface Expression {
    Money reduce(Bank bank, String to);
    Expression plus(Expression addend);
    Expression times(int multiplier);
}
```

```java
//Sum
@Override
public Expression times(int multiplier) {
    return new Sum(augend.times(multiplier), addend.times(multiplier));
}
```

```text
✅ $5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 + $5 = $10
$5 + $5에서 Money 반환하기
✅ Bank.reduce(Money)
✅ Money에 대한 통화 반환을 수행하는 Reduce
✅ Reduce(Bank, String)
✅ Sum.plus
✅ Expression.times
```
