# 14장. 바꾸기

**할 일 목록**

```text
$5 + 10CHF = $10 (환율 2:1 경우)
$5 + $5 = $10
$5 + $5에서 Money 반환하기
✅ Bank.reduce(Money)
Money에 대한 통화 반환을 수행하는 Reduce ⬅
Reduce(Bank, String)
```

## 환전 테스트 추가

```java
@Test
@DisplayName("다른 통화 reduce")
void testReduceMoneyDifferentCurrency() throws Exception {
    //given
    Bank bank = new Bank();

    //when
    bank.addRate("CHF", "USD", 2);
    Money result = bank.reduce(Money.franc(2), "USD");
    
    //then
    assertThat(result).isEqualTo(Money.dollar(1));
}
```

## reduce 리랙터링

```java
//Bank
public Money reduce(Expression source, String to) {
    return source.reduce(this, to);
}
```

```java
//Money
@Override
public Money reduce(Bank bank, String to) {
    int rate = bank.rate(currency, to);
    return new Money(amount / rate, to);
}
```

```java
public interface Expression {
    Money reduce(Bank bank, String to);
}
```

## 통화 리팩터링

```java
public class Pair {
    private String from;
    private String to;

    public Pair(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        Pair pair = (Pair) o;
        return from.equals(pair.from) && to.equals(pair.to);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
```

```java
public class Bank {

    private Hashtable<Pair, Integer> rates = new Hashtable<>();
    
    public Money reduce(Expression source, String to) {
        return source.reduce(this, to);
    }

    public void addRate(String from, String to, int rate) {
        rates.put(new Pair(from, to), Integer.valueOf(rate));
    }

    public int rate(String from, String to) {
        return rates.get(new Pair(from, to));
    }
}
```

동일 통화인 경우 1을 리턴해야 함
- 따로 테스트 작성

```java
@Test
@DisplayName("동일 통화인 경우 비율 1 리턴해야 함")
void testIdentityRate() throws Exception {
    int rate = new Bank().rate("USD", "USD");
    assertThat(rate).isEqualTo(1);
}
```

```java
public int rate(String from, String to) {
    if (from.equals(to)) {
        return 1;
    }
    return rates.get(new Pair(from, to));
}
```

**** 

- 필요할 거라고 생각한 인자를 빠르게 추가
- 코드와 테스트 사이에 있는 데이터 중복 제거
- 별도의 테스트 없이 도우미 클래스 생성
- 리팩터링 중 실수를 하고 이를 해결하기 위한 또다른 테스트 작성