# 18장. xUnit으로 가는 첫걸음

```text
테스트 메서드 호출하기 ⬅
먼저 setUp 호출하기
나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
수집된 결과를 출력하기
```

### 테스트 코드 

처음에는 프린트된 플래그를 확인해서 테스트 통과 여부 확인

```python
test = WasRun("testMethod")
print(test.wasRun)
test.testMethod()
print(test.wasRun)
```

### 클래스 구현

```python
class WasRun:
    def __init__(self, name):
        self.wasRun = None
        
    def testMethod(self):
        self.wasRun = 1
```

### 테스트 호출 인터페이스 생성

진짜 테스트 메서드를 수행하는 것이 아닌 테스트 인터페이스를 생성

```python
test = WasRun("testMethod")
print(test.wasRun)
test.run()
print(test.wasRun)
```

```python
#WasRun
def run(self):
    self.testMethod()
```

### 테스트 메서드 동적 호출

```python
class WasRun:
    def __init__(self, name):
        self.wasRun = None
        self.name = name

    def testMethod(self):
        self.wasRun = 1

    def run(self):
        method = getattr(self, self.name)
        method()
```
getattr로 리플렉션을 이용해서 메서드 동적으로 가져옴

### TestCase 클래스 생성

```python
class WasRun(TestCase):
    def __init__(self, name):
        self.wasRun = None
        TestCase.__init__(self, name)

    def testMethod(self):
        self.wasRun = 1

    def run(self):
        method = getattr(self, self.name)
        method()
```

```python
class TestCase:
    def __init__(self, name):
        self.name = name

    def run(self):
        method = getattr(self, self.name)
        method()
```

### 테스트 확인 코드 생성

None, 1이 프린트 됐는지 확인하는 것이 아닌 코드를 통해 확인

```python
class TestCaseTest(TestCase):
    def testRunning(self):
        test = WasRun("testMethod")
        assert(not test.wasRun)
        test.run()
        assert(test.wasRun)
```

```python
TestCaseTest("testRunning").run()
```

```text
✅ 테스트 메서드 호출하기
먼저 setUp 호출하기
나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
수집된 결과를 출력하기
```

- 하드코딩을 한 후 상수를 변수로 대체하여 일반성을 이끌어내는 방식으로 구현
- 플러거블 셀렉터를 사용
- 테스트 프레임워클르 작은 단계로만 부트스트랩함