# 23장. 얼마나 달콤한지

```text
✅ 테스트 메서드 호출하기
✅ 먼저 setUp 호출하기
✅ 나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기 ⬅
✅ 수집된 결과를 출력하기
✅ WasRun에 로그 문자열 남기기
✅ 실패한 테스트 보고하기
setUp 에러를 잡아서 보고하기
```

### TestSuite를 만들기

개별 테스트 실행은 테스트가 독립적으로 돌아가기 때문에 의미가 없음

컴포지트 패턴을 이용하여 TestSuite를 구현

### 테스트 코드 추가

```python
# TestCaseTest
def testSuite(self):
    suite = TestSuite()
    suite.add(WasRun("testMethod"))
    suite.add(WasRun("testBrokenMethod"))
    result = TestResult()
    suite.run(result)
    assert("2 run, 1 failed" == result.summary())
```

### 구현

```python
class TestSuite:
    def __init__(self):
        self.tests = []

    def add(self, test):
        self.tests.append(test)

    def run(self, result):
        for test in self.tests:
            test.run(result)
```

```python
# TestSuite
def run(self, result):
    result.testStarted()
    self.setUp()
    try:
        method = getattr(self, self.name)
        method()
    except Exception:
        result.testFailed()
    self.tearDown()
```

run의 매개 변수와 리턴이 바뀐 이유는 컴포지트 패턴을 적용했기 때문

- 컴포지트 패턴의 주요 제약 중 하나는 컬렉션이 하나의 개별 아이템인 것처럼 반응해야 한다는 것
- TestCase.run()에 매개 변수를 추가하면 TestSuite.run()에도 똑같은 매개 변수를 추가해야 함
  - run()이라는 공통 인터페이스를 가짐 - Component
  - TestSuite - Composite
  - TestCase - Leaf
  - Composite(TestSuite)는 run을 Leaf(TestCase)에 위임함

### 실패하는 테스트 수정

실패하는 테스트들은 기존의 인자 없는 run을 사용하기 때문에 실패

```python
class TestCaseTest(TestCase):
    def setUp(self):
        self.result = TestResult()

    def testTemplateMethod(self):
        self.test = WasRun("testMethod")
        self.test.run(self.result)
        assert ("setUp testMethod tearDown " == self.test.log)

    def testResult(self):
        self.test = WasRun("testMethod")
        self.test.run(self.result)
        assert("1 run, 0 failed" == self.result.summary())

    def testFailedResult(self):
        self.test = WasRun("testBrokenMethod")
        self.test.run(self.result)
        assert("1 run, 1 failed" == self.result.summary())

    def testFailedResultFormatting(self):
        self.result.testStarted()
        self.result.testFailed()
        assert("1 run, 1 failed" == self.result.summary())

    def testSuite(self):
        suite = TestSuite()
        suite.add(WasRun("testMethod"))
        suite.add(WasRun("testBrokenMethod"))
        suite.run(self.result)
        assert("2 run, 1 failed" == self.result.summary())
```

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