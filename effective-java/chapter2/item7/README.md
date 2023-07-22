# 불필요한 객체 생성을 피하라

```java
String s = new String(""); //x
String s = ""; //o
```

- 첫 코드는 실행될 때마다 String 인스턴스를 만듬
- 자바 리터럴 이용
- 정적 팩터리 패턴으로도 불필요한 객체 생성 막을 수 있음

```java
//x
static boolean isRomanNumeral(String s) {
    return s.matches(정규표현식);
}

//o
public class RomanNumerals {

    private static final Pattern ROMAN = Pattern.complie(졍규표현식);

    static boolean isRomanNumeral(String s) {
        return s.matcher(ROMAN).matches();
    }
}
```

첫 코드는 실행 시에 matches 내부에서 Pattern 인스턴스를 만들기 때문에 따로 패턴 인스턴스를 만드는 것이 효과적임

```java
private static long sum() {
    Long sum = 0L; //x
    for (long i = 0; i <= Integer.MAX_VALUE; i++) {
        sum += i;
    }
}
```

오토 박싱도 잘못 사용하면 쓸데 없는 인스턴스를 많이 만들게 됨

- 쓸모없는 객체 생성과 불변을 위한 방어적 복사와 헷갈리지 말자
