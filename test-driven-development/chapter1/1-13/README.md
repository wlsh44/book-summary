# 13장. 진짜로 만들기

**할 일 목록**

```text
$5 + 10CHF = $10 (환율 2:1 경우)
$5 + $5 = $10 ⬅
```

## 가짜 구현 진짜 구현으로 바꾸기

$5 + $5에 대한 작업에 불확실함이 있기 때문에 더 작은 방향으로 작업

```text
$5 + 10CHF = $10 (환율 2:1 경우)
$5 + $5 = $10 ⬅
$5 + $5에서 Money 반환하기
```

```java
@Test
@DisplayName("plus가 sum을 리턴해야 함")
void testPlusReturnsSum() throws Exception {
    //given
    Money five = Money.dollar(5);

    //when
    Expression result = five.plus(five);
    Sum sum = (sum) result;

    //then
    assertThat(five).isEqualTo(sum.augend);
    assertThat(five).isEqualTo(sum.addend);
}
```

## 클래스 구현

```java
//Money
public Expression plus(Money addend) {
    return new Sum(this, addend);
}
```

```java
public class Sum implements Expression {
    Money augend;
    Money addend;

    public Sum(Money augend, Money addend) {
        this.augend = augend;
        this.addend = addend;
    }
}
```

## bank 테스트 코드 추가

```java
@Test
@DisplayName("reduce sum 테스트")
void testReduceSum() throws Exception {
    //given
    Expression sum = new Sum(Money.dollar(3), Money.dollar(4));
    Bank bank = new Bank();

    //when
    Money result = bank.reduce(sum, "USD");

    //then
    assertThat(result).isEqualTo(Money.dollar(7));
}
```

```java
public Money reduce(Expression source, String to) {
    Sum sum = (Sum) source;
    int amount = sum.augend.amount + sum.addend.amount;
    return new Money(amount, to);
}
```

위 코드가 지저분한 이유
- 캐스팅 -> 이 코드는 모든 Expression에 대해 작동해야 함
- 공용(public) -> 필드와 그 필드들에 대한 두 단게에 걸친 레퍼런스

```java
public Money reduce(Expression source, String to) {
    Sum sum = (Sum) source;
    return sum.reduce(to);
}
```

```java
//Sum
public Money reduce(String to) {
    int amount = augend.amount + addend.amount;
    return new Money(amount, to);
}
```

## reduce money 테스트 추가

```java
@Test
@DisplayName("reduce money 테스트")
void testReduceMoney() throws Exception {
    Bank bank = new Bank();

    //when
    Money result = bank.reduce(Money.dollar(1), "USD");

    //then
    assertThat(result).isEqualTo(Money.dollar(1));
}
```

```java
public class Bank {
    public Money reduce(Expression source, String to) {
        if (source instanceof Money) {
            return (Money) source.reduce(to); 
        }
        Sum sum = (Sum) source;
        return sum.reduce(to);
    }
}
```

## reduce 리팩터링

캐스팅이 지저분하므로 인터페이스를 이용해 다형성으로 해결

```java
//Money
@Override
public Money reduce(String to) {
    return this;
}
```

```java
public interface Expression {
    Money reduce(String to);
}
```

```java
public class Bank {
    public Money reduce(Expression source, String to) {
        return source.reduce(to);
    }
}
```

Expression과 Bank에 이름은 같지만 매개변수가 다른 메서드가 존재하는게 별로임
- Expression.reduce(String)과 Bank.reduce(Expression, String)의 차이를 한 눈에 알기 힘듬
- 위치 매개 변수의 한계

****

- 모든 중복이 제거되기 전까지는 테스트를 통과한 것으로 치지 않음
- 구현하기 위해 역방향이 아닌 순방향으로 작업
- 필요할 것으로 예상되는 객체의 생성을 강요하기 위한 테스트 작성
- 캐스팅을 이용해 구현 -> 다형성으로 리팩터링