# 생성자에 매개변수가 많다면 빌더를 고려하라

## TL;DR


## 점층적 생성자 패턴 문제점

```java

public class NutritionFacts {
  private final int servingSize;
  private final int servings;
  private final int calories;
  private final int fat;
  private final int sodium;
  private final int carbohydrate;
  
  public NutritionFacts(int servingSize, int servings) {
    this(servingSize, servings, 0);
  }
  
  public NutritionFacts(int ServingSize, int servings, int calories) {
    this(servingSize, servings, calories, 0);
  }
  
  ...
  
  public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate) {
    this.servingSize = servingSize;
    ...
  }
}
```

**문제점**

- 매개 변수 개수가 많아지면 클라이언트 코드를 작성하거나 읽기 어려움
- 잘못해서 순서가 바뀌면 런타임에서 예외가 발생할 수 있음

## 자바빈즈 패턴

점층적 생성자 패턴의 대안으로, getter와 setter 사용

```java
public class NutritionFacts {
  private final int servingSize = -1; // 필수, 기본 값 없음
  private final int servings = -1; // 필수, 기본 값 없음
  private final int calories = 0;
  private final int fat = 0;
  private final int sodium = 0;
  private final int carbohydrate = 0;
  
  public NutritionFacts() {}

  public void setServingSize(int val) {servingSize = val;}
  public void setServings(int val) {servings = val;}
  ...
}
```

**문제점**

- 객체 하나를 만들기 위해 여러 메서드를 호출해야 함
- 객체가 완전히 생성되기 전까지 일관성(consistency)가 무너진 상태가 됨 
  - 일관성이 깨짐 -> 버그를 발생하는 코드가 물리적으로 떨어져있기 때문에 디벼깅 문제도 발생
- **불변 클래스를 만들 수 없음**
  - 스레드 안정성을 얻을 수 없음

## 빌터 패턴


```java
public class NutritionFacts {
  private final int servingSize;
  private final int servings;
  private final int calories;
  private final int fat;
  private final int sodium;
  private final int carbohydrate;

  public static class Builder {
    private final int servingSize;
    private final int servings;

    private final int calories = 0;
    private final int fat = 0;
    private final int sodium = 0;
    private final int carbohydrate = 0;

    public Builder(int servingSize, int servings) {
      this.servingSize = servingSize;
      this.servings = servings;
    }

    public Builder calories(int val) {
      calories = val;
      return this;
    }
    
    public Builder fat(int val) {
      fat = val;
      return this;
    }
    ...
    
    public NutrionFacts build() {
      return new NutrionFacts(this);
    }
  }
  
  private NutrionFacts(Builder builder) {
    servingSize = builder.servingSize;
    servings = builder.servings;
    calories = builder.calories;
    ...
  }
}
```

