# try-finally보다는 try-with-resources를 사용하라

```java
static String firstLineOfFile(String path) throws IOException {
    BufferedReader br = new BufferReader(new FileReader(path)) {
        try {
            return br.readline();
        } finally {
            br.close();
        }
    }
}
```

- 만약 close에서도 실패가 일어나면 첫 번째 예외 트레이스가 사라짐
- 자바7 이후부터 제공하는 AutoCloseable 인터페이스 구현
- close가 여러개일 경우 try 중첩으로 사용하지 않아도 됨

```java
static String firstLineOfFile(String path) throws IOException {
    try (BufferedReader br = 
            new BufferReader(new FileReader(path))) {
        return br.readline();
        } 
    }
}
```
