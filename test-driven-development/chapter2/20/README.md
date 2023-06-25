# 20장. 뒷정리하기

```text
✅ 테스트 메서드 호출하기
✅ 먼저 setUp 호출하기
나중에 tearDown 호출하기 ⬅
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
수집된 결과를 출력하기
WasRun에 로그 문자열 남기기 ⬅
```

### wasSetUp 플래그 제거

```python
# WasRun
def setUp(self):
    self.wasRun = None
    self.log = "setUp "
```

```python
# TestCaseTest
def testSetUp(self):
    self.test.run()
    assert ("setUp " == self.test.log)
```

### wasRun 제거

```python
# WasRun
def testMethod(self):
    self.wasRun = 1
    self.log += "testMethod "
```

```python
# TestCaseTest
def testSetUp(self):
    self.test.run()
    assert ("setUp testMethod " == self.test.log)
```

testRunning 제거, testSetUp 이름 변경

```python
class TestCaseTest(TestCase):
    def testTemplateMethod(self):
        self.test = WasRun("testMethod")
        self.test.run()
        assert ("setUp testMethod " == self.test.log)
```

### tearDown 테스트 코드 추가

```python
class TestCaseTest(TestCase):
    def testTemplateMethod(self):
        self.test = WasRun("testMethod")
        self.test.run()
        assert ("setUp testMethod tearDown " == self.test.log)
```

### tearDown 구현

```python
## TestCase
def tearDown(self):
    pass

def run(self):
    self.setUp()
    method = getattr(self, self.name)
    method()
    self.tearDown()
```

```python
# WasRun
def tearDown(self):
    self.log += "tearDown "
```

```text
✅ 테스트 메서드 호출하기
✅ 먼저 setUp 호출하기
✅ 나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
수집된 결과를 출력하기
✅ WasRun에 로그 문자열 남기기
```