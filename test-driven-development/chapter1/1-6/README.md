# 6장. 돌아온 '모두를 위한 평등'

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
공용 equals ⬅
공용 times
```
### 1. Money 클래스 생성

```java
public class Money {}
```

### 2. Money 상속

```java
public class Dollar extends Money {}
```

### 3. amount 이동

```java
public class Money {
    protected int amount;
}
```

### 4. equals 변경

```java
//dollar
public boolean equals(Object obj) {
    Money dollar = (Dollar) obj;
    return amount == dollar.amount;
}
```

```java
//dollar
public boolean equals(Object obj) {
    Money moeny = (Money) obj;
    return amount == money.amount;
}
```

### 5. equals Money로 이동

```java
//money
public boolean equals(Object obj) {
    Money moeny = (Money) obj;
    return amount == money.amount;
}
```

### 6. Franc도 적용

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
```

- 공통된 코드를 첫 번째 클래스(Dollar)에서 상위 클래스(Money)로 단계적으로 옮김
- 두 번째 클래스(Franc)도 Money의 하위 클래스로 만듦
- 불필요한 구현을 제거하기 전에 두 equals 구현을 일치시킴