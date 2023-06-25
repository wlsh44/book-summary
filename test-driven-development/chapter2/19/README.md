# 19장. 테이블 차리기

```text
✅ 테스트 메서드 호출하기
먼저 setUp 호출하기 ⬅
나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
수집된 결과를 출력하기
```

3A 패턴
1. 준비(Arrange) - 객체를 생성
2. 행동(Act) - 어떤 자극을 줌
3. 확인(Assert) - 결과를 검사

- 성능 - 테스트는 가능한 빨리 실행되어야 함. 여러 테스트에서 같은 객체를 사용한다면, 객체 하나만 생성해서 모든 테스트가 이 객체를 쓰게 할 수 있을 것임
- 격리 - 한 테스트에서의 성공이나 실패가 다른 테스트에 영향을 주지 않기를 원함. 만약 테스트들이 객체를 공유하는 상태에서 하나의 테스트가 공유 객체의 상태를 변경한다면 다음 테스트의 결과에 영향을 미칠 가능성이 있음

### 테스트 코드 작성

```python
# TestCaseTest
def testSetUp(self):
    test = WasRun("testMethod")
    test.run()
    assert (test.wasSetUp)
```

### 클래스 작성

```python
# WasRun
def setUp(self):
    self.wasSetUp = 1
```

```python
# TestCase
def setUp(self):
    pass

def run(self):
    self.setUp()
    method = getattr(self, self.name)
    method()
```

> 당신이 뭔가를 배우고 싶다면, 한 번에 메서드를 하나 이상 수정하지 않으면서 테스트가 통과하게 만들 수 있는 방법을 찾아내려고 노력해라.

```python
# WasRun
def setUp(self):
    self.wasRun = None
    self.wasSetUp = 1
```

각 테스트 메서드는 새 TestCaseTest 인스턴스를 사용하므로 두 개의 테스트가 커플링될 가능성이 없음

```python
class TestCaseTest(TestCase):
    def setUp(self):
        self.test = WasRun("testMethod")
    
    def testSetUp(self):
        self.test.run()
        assert (self.test.wasSetUp)

    def testRunning(self):
        self.test.run()
        assert(self.test.wasRun)
```

```text
✅ 테스트 메서드 호출하기
✅ 먼저 setUp 호출하기
나중에 tearDown 호출하기
테스트 메서드가 실패하더라도 tearDown 호출하기
여러 개의 테스트 실행하기
수집된 결과를 출력하기
```