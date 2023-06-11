# 12장. 드디어, 더하기

**할 일 목록**

```text
$5 + 10CHF = $10 (환율 2:1 경우) ⬅
```

## 더하기 테스트 작성

```java
@Test
@DisplayName("간단한 더하기 테스트")
void testSimpleAddition() throws Exception {
    //given
    Money dollar = Money.dollar(5);

    //when
    Money sum = dollar.plus(Money.dollar(5));

    //then
    assertThat(sum).isEqualTo(Money.dollar(10));
}
```

> TDD는 적절한 때에 번뜩이는 통찰을 보장하지 못한다. 그렇지만 확신을 주는 테스트와 조심스럽게 정리된 코드를 통해, 통찰에 대한 준비와 함께 통찰이 번뜩일 때 그걸 적용할 준비를 할 수 있다.

## 단일 통화로 계산하는 테스트로 변경

```java
@Test
@DisplayName("간단한 더하기 테스트")
void testSimpleAddition() throws Exception {
    //given
    Money dollar = Money.dollar(5);
    
    //when
    Money sum = dollar.plus(Money.dollar(5));
    Money reduced = bank.reduce(sum, "USD");

    //then
    assertThat(reduced).isEqualTo(Money.dollar(10));
}
```

연산의 결과로 생긴 Expression들을 단일 통화로 바꿔주는 bank 추가

- 왜 Bank가 reduce()를 수행하는 책임을 맡는가?
- 그게 제일 먼저 떠올랐다 -> 좋지 않은 생각
- Expression은 우리가 하려고 하는 일의 핵심
- 핵심이 되는 객체가 다른 부분에 대해서 될 수 있는 한 모르도록 노력 -> 캡슐화
- Expression과 관련이 있는 오퍼레이션이 많을 거라고 상상할 수 있음

bank가 필요 없다면 축약을 구현할 책임을 Expression으로 넘겨도 됨

```java
@Test
@DisplayName("간단한 더하기 테스트")
void testSimpleAddition() throws Exception {
    //given
    Money five = Money.dollar(5);
    Bank bank = new Bank();
    
    //when
    Expression sum = five.plus(Money.dollar(5));
    Money reduced = bank.reduce(sum, "USD");

    //then
    assertThat(reduced).isEqualTo(Money.dollar(10));
}
```

## 클래스 생성

```java
public interface Expression {
}
```

```java
public class Money implements Expression {
    public Expression plus(Money dollar) {
        return new Money(dollar.amount + amount, currency);
    }
}
```

```java
public class Bank {
    public Money reduce(Expression sum, String usd) {
        return Money.dollar(10);
    }
}
```

## 테스트

- 큰 테스트($5 + 10CHF에서 $5 + $5)로 줄여서 발전할 수 있도록 구현
- 계산에 필요한 메타포 생각
- 새 메타포 기반 테스트 작성
- 테스트 컴파일
