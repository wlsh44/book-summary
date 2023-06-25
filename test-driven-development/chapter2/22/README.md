# 22장. 실패 처리하기

```text
✅ 테스트 메서드 호출하기
✅ 먼저 setUp 호출하기
✅ 나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
✅ 수집된 결과를 출력하기
✅ WasRun에 로그 문자열 남기기
실패한 테스트 보고하기 ⬅
```

### 테스트 추가

```python
def testFailedResultFormatting(self):
    result = TestResult()
    result.testStarted()
    result.testFailed()
    assert("1 run, 1 failed" == result.summary())
```

### 구현

```python
class TestResult:
    def __init__(self):
        self.failureCount = 0
        self.runCount = 0

    def testStarted(self):
        self.runCount += 1

    def summary(self):
        return f"{self.runCount} run, {self.failureCount} failed"

    def testFailed(self):
        self.failureCount += 1
```

- setUp, tearDown의 예외는 잡히지 않는다는 문제점이 존재함

```text
✅ 테스트 메서드 호출하기
✅ 먼저 setUp 호출하기
✅ 나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
✅ 수집된 결과를 출력하기
✅ WasRun에 로그 문자열 남기기
✅ 실패한 테스트 보고하기
setUp 에러를 잡아서 보고하기
```