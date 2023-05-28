# 2장. 타락한 객체

**할 일 목록**

```text
$5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 * 2 = $10
amount를 private로 만들기
Dollar의 부작용? ⬅
Money 반올림?
```

테스트를 다음처럼 변경한다면?

```java
@Test
@DisplayName("달러에 수를 곱한 결과를 얻어야 함")
void testMultiplication() throws Exception {
    Dollar five = new Dollar(5);
    five.times(2);
    assertThat(five.amount).isEqualTo(10);
    five.times(3);
    assertThat(five.amount).isEqualTo(15);
}
```

### 1. 인터페이스, 테스트를 수정

```java
@Test
@DisplayName("달러에 수를 곱한 결과를 얻어야 함")
void testMultiplication() throws Exception {
    Dollar five = new Dollar(5);
    Dollar product = five.times(2);
    assertThat(product.amount).isEqualTo(10);
    product = five.times(3);
    assertThat(product.amount).isEqualTo(15);
}
```

### 2. 컴파일이 되지 않으므로 코드 수정

```java
public Dollar times(int multiplier) {
    amount *= multiplier;
    return null;
}
```

### 3. 테스트 후 실패 확인

### 4. 코드 수정

```java
public Dollar times(int multiplier) {
    return new Dollar(amount * multiplier);
}
```

**할 일 목록**

```text
$5 + 10CHF = $10 (환율 2:1 경우)
✅ $5 * 2 = $10
amount를 private로 만들기
✅ Dollar의 부작용?
Money 반올림?
```

### 테스트를 성공시키는 가장 빠른 방법

- 가짜로 구현하기: 상수 반환 후 변수로 변환
- 명백한 구현 사용: 실제 구현 입력

**내가 뭘 입력해야 할지 알 때는 명백한 구현, 예상치 못한 실패를 하면 가짜로 구현**

