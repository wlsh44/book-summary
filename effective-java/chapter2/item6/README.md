# 다 쓴 객체는 참조를 해제하라

- 참조를 해제하는 가장 좋은 방법 -> 변수를 scope 밖으로 보내기

```java
import java.util.EmptyStackException;

public class Stack {

    private Object[] elements;
    
    ...

    public Object pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return elements[size--];
    }
}
```

- 이 경우 JVM은 elements의 참조를 다 해제한지 모르기 때문에 메모리를 낭비하게 됨
- 이런 경우 null 처리를 해서 GC가 이뤄지도록 해야 함


- 캐시나 리스너, 콜백도 메모리 누수의 주범

