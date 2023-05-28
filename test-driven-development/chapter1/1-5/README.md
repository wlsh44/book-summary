# 4장. 프라이버시

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
5CHF * 2 = 100CHF ⬅
```

### 1. 테스트 작성

```java
@Test
@DisplayName("프랑에 수를 곱한 결과를 얻어야 함")
void testFrancMultiplication() throws Exception {
    //given
    Franc five = new Franc(5);

    //when
    Franc product1 = five.times(2);
    Franc product2 = five.times(3);

    //then
    assertThat(product1).isEqualTo(new Franc(10));
    assertThat(product2).isEqualTo(new Franc(15));
}
```

### 2. 컴파일 되게 하기

```java
public class Franc {

    private int amount;

    public Franc(int amount) {
        this.amount = amount;
    }

    public Franc times(int multiplier) {
        return new Franc(amount * multiplier);
    }

    @Override
    public boolean equals(Object obj) {
        Franc franc = (Franc) obj;
        return amount == franc.amount;
    }
}
```

### 3. 실패하는지 확인하기 위해 실행

### 4. 실행하게 만듦

### 5. 중복 제거

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
공용 equals
공용 times
```

- 큰 테스트는 공략할 수 없다. 그래서 진전을 나타낼 수 있는 자그마한 테스트를 만들었다
- 중복을 만들고 조금 고쳐서 테스트를 작성했다
- 복사하고 수정해서 테스트를 통과했다
- 중복을 제거한다