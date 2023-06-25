# 21장. 셈하기

```text
✅ 테스트 메서드 호출하기
✅ 먼저 setUp 호출하기
✅ 나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
수집된 결과를 출력하기 ⬅
✅ WasRun에 로그 문자열 남기기
```

### 테스트 추가

```python
def testResult(self):
    self.test = WasRun("testMethod")
    result = self.test.run()
    assert("1 run, 0 failed" == result.summary())
```

### 클래스 구현

```python
class TestResult:
    def summary(self):
        return "1 run, 0 failed"
```

```python
# TestCase
def run(self):
    self.setUp()
    method = getattr(self, self.name)
    method()
    self.tearDown()
    return TestResult()
```

### 로그 라팩터링

```python
class TestResult:
    def __init__(self):
        self.runCount = 1
    
    def testStarted(self):
        self.runCount = self.runCount + 1
    
    def summary(self):
        return f"{self.runCount} run, 0 failed"
```

```python
# TestCase
def run(self):
    result = TestResult()
    result.testStarted()
    self.setUp()
    method = getattr(self, self.name)
    method()
    self.tearDown()
    return result
```

### 테스트 실패 테스트 케이스 추가

```python
# TestCaseTest
def testFailedResult(self):
    self.test = WasRun("testMethod")
    result = self.test.run()
    assert("1 run, 1 failed" == result.summary())
```

```python
# WasRun
def testBrokenMethod(self):
    raise Exception
```

```text
✅ 테스트 메서드 호출하기
✅ 먼저 setUp 호출하기
✅ 나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
✅ 수집된 결과를 출력하기
✅ WasRun에 로그 문자열 남기기
실패한 테스트 보고하기
```