# 7장. 사과와 오렌지

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
Dollar/Franc 중복
✅ 공용 equals
공용 times
Franc와 Dollar 비교하기 ⬅
```

### Dollar와 Franc 비교

```java
@Test
@DisplayName("값 객체 동등성 테스트")
void testEquality() throws Exception {
    assertThat(new Dollar(5).equals(new Dollar(5))).isTrue();
    assertThat(new Dollar(5).equals(new Dollar(6))).isFalse();
    assertThat(new Franc(5).equals(new Franc(5))).isTrue();
    assertThat(new Franc(5).equals(new Franc(6))).isFalse();
    assertThat(new Franc(5).equals(new Dollar(5))).isFalse(); //테스트 추가
}
```

테스트 실패 함

### equals 수정

```java
@Override
public boolean equals(Object obj) {
    Money money = (Money) obj;
    return amount == money.amount &&
            getClass().equals(money.getClass());
}
```

통화 개념 없이 수정

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
Dollar/Franc 중복
✅ 공용 equals
공용 times
통화?
```

- 결함을 테스트에 담아냄
- 테스트 통과시킴
- **현재 통화 개념을 도입할 충분한 이유가 없어 보이므로 설계를 추가하지 않음**

