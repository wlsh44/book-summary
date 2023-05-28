# 4장. 프라이버시

**할 일 목록**

```text
$5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 * 2 = $10
amount를 private로 만들기 ⬅
✅ Dollar의 부작용?
Money 반올림?
✅ equals()
hashCode()
Equal null
Equal object
```

### 테스트 코드 수정

```java
@Test
@DisplayName("달러에 수를 곱한 결과를 얻어야 함")
void testMultiplication() throws Exception {
    //given
    Dollar five = new Dollar(5);

    //when
    Dollar product1 = five.times(2);
    Dollar product2 = five.times(3);

    //then
    assertThat(product1).isEqualTo(new Dollar(10));
    assertThat(product2).isEqualTo(new Dollar(15));
}
```

### 코드 리팩터링

```java
public class Dollar {

    private int amount;
    
    //...
}
```

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
```

지금까지 배운 것
- 오직 테스트를 향상시키기 위해서만 개발된 기능 사용
- 두 테스트가 동시에 실패하면 망함
- 위험 요소가 있음에도 계속 진행
- 테스트와 코드 사이의 결합도를 낮추기 위해 테스트하는 객체의 새 기능을 사용