# 11장. 모든 악의 근원

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
testFrancMultiplication 제거 
```

## 하위 클래스 의존성 제거

```java
public static Money dollar(int amount) {
    return new Money(amount, "USD");
}

public static Money franc(int amount) {
    return new Money(amount, "CHF");
}
```

## 중복 테스트 제거

testEquality, testFrancMultiplication, testDifferenceClassEquality 테스트 중복 제거
=> 테스트 코드에 Money만 남기기

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
✅ testFrancMultiplication 제거
```


