# 생성자에 매개변수가 많다면 빌더를 고려하라

## TL;DR

- 매개변수가 많을 경우, 빌더를 사용하자
- 객체의 불변도 지키고 가독성도 더 

> 생성자나 정적 팩터리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하는 게 더 낫다.<br>
> 매개변수 중 다수가 필수가 아니거나 같은 타입이면 특히 더 그렇다.<br>
> 빌더는 점층적 생성자보다는 클라이언트 코드를 읽고 쓰기가 훨씬 간결하고, 자바빈즈보다 훨씬 안전하다.

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

장점
- 불변 객체를 만들 수 있음
- builder 호출하는 동안 불변식(invariant)을 추가할 수 있기 때문에, 잘못된 매개 변수를 최대한 일찍 발견할 수 있음

> 불변(immutable): 어떠한 변경도 허용하지 않는다는 뜻
> 불변식(invariant): 프로그램이 실행되는 동안 반드시 만족해야 하는 조건, 변경은 허용하나 조건식을 만족해야 함

### 빌더 패턴은 계층적으로 설계된 클래스와 함께 쓰기에 좋다.

```java
public abstract class Pizza {
  public enum Topping {HAM, MUSHROOM, ONION, PEPPER, SAUSAGE}
  final Set<Topping> toppings;
  
  abstract static class Builder<T extends Builder<T>> {
    EnumSet<Topping> toppings = EnumSet.noneOf(Topping.class);
    public T addTopping(Topping topping) {
      toppings.add(Objects.requireNonNull(topping));
      return self();
    }
    
    abstract Pizza build();
    
    protected abstract T self();
  }
  
  Pizza(Builder<?> builder) {
    toppings = builder.toppings.clone();
  }
}
```

- self를 통해 형변환 없이 메서드 연쇄를 지원할 수 있음

```java
public class NyPizza extends Pizza {
  public enum Size {SMALL, MEDIUM, LARGE}
  private final Size size;
  
  public static class Builder extends Pizza.Builder<Builder> {
    private final Size size;
    
    public Builder(Size size) {
      this.size = Objects.requireNonNull(size);
    }
    
    @Override
    public NyPizza build() {
      return new NyPizza(this);
    }
    
    @Override
    protected builder self() {
      return this;
    }
  }
  
  private NyPizza(Builder builder) {
    super(builder);
    size = builder.size;
  }
}

NyPizza pizza = new NyPizza.Builder(SMALL)
                            .addTopping(SAUSAGE)
                            .addTopping(ONION)
                            .build();
```

장점
- 여러 객체를 순회하면서 만들 수 있음
- 넘기는 매개변수에 따라 다른 객체를 만들 수 있음

단점
- 빌더를 만들어야 하는 비용
- 매개변수가 많아야 값어치를 함
- **API가 변할 떄 생기는 잠재적 버그** <- 개인적으로 이게 제일 중요하다고 생각함
  - 필드만 만들고 빌더를 만드는 과정에서 추가를 안 한 경우 
