# 15장. 서로 다른 통화 더하기

**할 일 목록**

```text
$5 + 10CHF = $10 (환율 2:1 경우) ⬅
✅ $5 + $5 = $10
$5 + $5에서 Money 반환하기
✅ Bank.reduce(Money)
✅ Money에 대한 통화 반환을 수행하는 Reduce ⬅
✅ Reduce(Bank, String)
```

## 서로 다른 통화 더하기 테스트 추가

```java
@Test
@DisplayName("다른 통화 더하기 테스트")
void testMixedAddition() throws Exception {
    //given
    Money fiveBucks = Money.dollar(5);
    Money tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);

    //when
    Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");

    //then
    assertThat(result).isEqualTo(Money.dollar(10));
}
```

## 코드 수정

테스트가 실패하므로 Sum의 reduce 수정

```java
//Sum
@Override
public Money reduce(Bank bank, String to) {
    int amount = augend.reduce(bank, to).amount + addend.reduce(bank, to).amount;
    return new Money(amount, to);
}
```

Sum Money 제거

```java
//Money
@Override
public Money reduce(Bank bank, String to) {
    int rate = bank.rate(currency, to);
    return new Money(amount / rate, to);
}
```

```java
public class Sum implements Expression {
    Expression augend;
    Expression addend;

    public Sum(Expression augend, Expression addend) {
        this.augend = augend;
        this.addend = addend;
    }
}
```

## Money 리팩터링

```java
//Money
@Override
public Expression plus(Expression addend) {
    return new Sum(this, addend);
}

public Expression times(int multiplier) {
    return new Money(amount * multiplier, currency);
}
```

## 테스트 코드 수정

동일 통화인 경우 1을 리턴해야 함
- 따로 테스트 작성

```java
@Test
@DisplayName("다른 통화 더하기 테스트")
void testMixedAddition() throws Exception {
    //given
    Expression fiveBucks = Money.dollar(5);
    Expression tenFrancs = Money.franc(10);
    Bank bank = new Bank();
    bank.addRate("CHF", "USD", 2);

    //when
    Money result = bank.reduce(fiveBucks.plus(tenFrancs), "USD");

    //then
    assertThat(result).isEqualTo(Money.dollar(10));
}
```

## Expression 수정

```java
public interface Expression {
    Money reduce(Bank bank, String to);
    Expression plus(Expression addend);
}
```

**** 

```text
✅ $5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 + $5 = $10
$5 + $5에서 Money 반환하기
✅ Bank.reduce(Money)
✅ Money에 대한 통화 반환을 수행하는 Reduce
✅ Reduce(Bank, String)
Sum.plus
Expression.times
```

- 원하는 테스트 작성, 한 단계 달성할 수 있도록 뒤로 물렀음
- 추상적인 선언을 통해 가지에서 뿌리로 일반화
- Expression fiveBucks 변경 후 다른 코드들 수정