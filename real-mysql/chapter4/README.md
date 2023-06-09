# 4.1 트랜잭션

트랜잭션
- 논리적인 작업 셋을 모두 완벽하게 처리하거나, 또는 처리하지 못할 경우 원 상태로 복구해서 작업의 일부만 적용되는 현상을 발생하지 않게 만들어주는 기능

트랜잭션 vs 락
- 락: 동시성 제어 기능
- 트랜잭션: 데이터 정합성 보장 기능

## 4.1.1 MySQL에서의 트랜잭션

- MyISAM, MEMORY은 트랜잭션 지원 x
  - 쿼리 중 오류가 발생하면 이전까지 성공했던 동작들은 부분 업데이트로 저장이 됨
  - 데이터 정합성 맞추기 힘들어짐

## 4.1.2 주의사항

트랜잭션은 최소의 코드에만 적용하는 것이 좋음

예시1
```text
1) 처리 시작
==> 데이터베이스 커넥션 생성
==> 트랜잭션 시작
2) 사용자의 로그인 여부 확인
3) 사용자의 글쓰기 내용의 오류 여부 확인
4) 첨부로 업로드된 파일 확인 및 저장
5) 사용자의 입력 내용을 DBMS에 저장
6) 첨부 파일 정보를 DBMS에 저장
7) 저장된 내용 또는 기타 정보를 DBMS에서 조회
8) 게시물 등록에 대한 알림 메일 발송
9) 알림 메일 발송 이력을 DBMS에 저장
<== 트랜잭션 종료(commit)
<== 데이터베이스 커넥션 반납
10) 처리 완료
```

문제점
- 실제로 DBMS에 데이터를 저장하는 작업은 5분부터 시작
- 2, 3, 4번은 트랜잭션에 포함시킬 필요가 없음
- 커넥션을 오래 소유할수록 여유 커넥션 수 줄어듬 -> 병목현상
- 8번처럼 메일 전송이나 파일 전송 작업과 같이 네트워크를 통해 원격 서버와 통신하는 작업은 DBMS의 트랜잭션 내에서 제거하는게 좋음
- 사용자가 입력한 정보 저장(5, 6)은 하나의 트랜잭션
- 7번은 단순 조회이므로 트랜잭션 필요 없음
- 9번 작업은 성격이 다르므로 분리

변경 후
```text
1) 처리 시작
2) 사용자의 로그인 여부 확인
3) 사용자의 글쓰기 내용 오류 여부 확인
4) 첨부로 업로드된 파일 확인 및 저장
==> 데이터베이스 커넥션 생성
==> 트랜잭션 시작
5) 사용자의 입력 내용을 DBMS에 저장
6) 첨부 파일 정보를 DBMS에 저장
<== 트랜잭션 종료(commit)
7) 저장된 내용 또는 기타 정보를 DBMS에서 조회
8) 게시물 등록에 대한 알림 메일 발송
==> 트랜잭션 시작
9) 알림 메일 발송 이력을 DBMS에 저장
<== 트랜잭션 종료(commit)
<== 데이터베이스 커넥션 반납
10) 처리 완료
```

## 4.2 MySQL 엔진의 잠금

MySQL의 잠금
- 스토리지 엔진 레벨
  - 스토리지 엔진 간 상호 영향을 미치지 않음
- MySQL 엔진 레벨
  - 모든 스토리지 엔진에 영향을 미침

### 4.2.1 글로벌 락

- MySQL에서 제공하는 잠금 가운데 가장 범위가 큼
- 한 세션이 글로벌 락을 얻으면 다른 세션들은 SELECT를 제외한 대부분의 쿼리는 락이 해제될 때까지 대기 상태로 남음

### 4.2.2 테이블 락

- 개별 테이블 단위 락
- MyISAM이나 MEMORY는 데이터가 변경될 때, 묵시적 테이블 락을 걸음
- InnoDB는 레코드 단위 락이 있기 때문에 묵시적 테이블 락이 걸리지는 않음

### 4.2.3 유저 락

- 테이블이나 레코드가 아닌 사용자가 지정한 특정 문자열에 대한 락
- 많은 레코드를 한 번에 변경하는 트랜잭션의 경우 유용하게 사용할 수 있음

### 4.2.4 네임 락

- 데이터베이스 객체(테이블, 뷰 등)의 이름을 변경하는 경우 획득하는 락
- 명시적으로 획득하는 것이 아닌, `RENAME TABLE a TO b` 같이 테이블 이름을 변경하면 자동으로 획득

## 4.4 InnoDB 스토리지 엔진의 잠금

### 4.4.1 InnoDB의 잠금 방식

**비관적 잠금(Pessimistic Lock)**
- 현재 트랜잭션에서 변경하고자 하는 레코드에 대해 잠금을 획득하고 변경 작업을 처리하는 방식
- 다른 트랜잭션에서 해당 레코드를 변경할 수 있다는 비관적 가정

**낙관적 잠금(Optimistic Lock)**
- 변경 작업을 수행하고 충돌이 있으면 롤백하는 방식
- 각 트랜잭션이 같은 레코드를 변경할 가능성은 상당히 희박할 것이라는 낙관적 가정

### 4.4.2 InnoDB 잠금 종류

**레코드 락(Record Lock)**
- 레코드 자체만을 잠그는 방식
- 다른 DBMS의 레코드락과의 차이점
  - InnoDB는 레코드 자체가 아니라 인덱스의 렉코드를 잠금
  - 인덱스가 하나도 없는 테이블이라면 클러스터 인덱스를 이용해 잠금 설정
- PK, UK 인덱스에 의한 변경 작업은 레코드 자체에 대해서만 락을 검

**갭 락(Gap Lock)**
- 레코드 자체가 아니라 레코드와 바로 인접한 레코드 사이의 간격을 잠그는 방식
- 다른 DBMS와의 차이
- 레코드와 레코드 사이의 간격에 새로운 레코드가 생성(INSERT)되는 것을 제어
- 갭 락은 자체적으로 사용되지 않고, 넥스트 키 락의 일부로 사용되는 개념

**넥스트 키 락(Next Key Lock)**
- 레코드 락과 갭 락을 합쳐 놓은 형태의 잠금 방식
- 갭 락과 넥스트 키 락은 바이너리 로그에 기록되는 쿼리가 슬레이브에서 실행될 때 마스터에서 만들어 낸 결과와 동일한 결과를 만들어내도록 보장하는 것이 목적

**자동 증가 락(Auto Increment Lock)**
- auto increment 속성이 적용된 테이블에 동시에 여러 레코드가 INSERT되는 경우 중복되지 않게 값을 증가시키기 위한 잠금 방식
- INSERT, REPLACE 쿼리에만 적용됨
- 트랜잭션과 상관 없이 auto increment에 필요한 값만 가져온 후 락이 해제됨
- 묵시적으로 걸리는 락

### 4.4.3 인덱스와 잠금

- InnoDB는 레코드를 잠그는 것이 아닌 인덱스를 잠금

예제
```sql
-- // employee 테이블 30만건
-- // first_name 인덱스 걸려있음
-- // first_name='Gerorgi'인 사원 253명
-- // first_name='Gerorgi'이고 last_name='Klassen'인 사원 1명

-- // first_name='Gerorgi'이고 last_name='Klassen'인 사원 입사 일자 변경하는 경우
UPDATE emplyees SET hire_date=NOW() WHERE first_name='Georgi' and last_name='Klassen';
```

- 1건의 레코드만 업데이트가 됨
- 이 경우 인덱스를 적용할 수 있는 조건 -> first_name='Georgi'
- 따라서 first_name='Georgi'인 253개의 레코가 모두 잠김
- 인덱스가 하나도 없다면 30만건의 모든 레코드를 잠금 -> index 설계가 중요한 이유

### 4.4.4 트랜잭션 격리 수준

- 위의 상황이 발생한 이유는 InnoDB의 넥스트 키 락 때문
- 트랜잭션 수준을 READ-COMMITED로 설정하고, 바이너리 로그 비활성화 또는 레코드 기반 바이너리 로그를 사용하면 대부분의 넥스트 키 락을 제거할 수 있음
  - 유니크키나 외래키에 대한 갭 락은 없어지지 않음
- 위처럼 설정을 하면 처음 쿼리에서 인덱스를 이용할 때 잠금을 걸었다가, 나머지 조건(last_name='Klassen')을 비교하는 단계에서 일치하지 않은 레코드는 모두 잠금을 해제함
  - `first_name='Georgi' and last_name='Klassen'`인 레코드에만 배타적 잠금을 가짐

### 4.4.5 레코드 수준의 잠금 확인 및 해제

| 커넥션 1                                                             | 커넥션 2                                                             | 커넥션 3                                                             |
|-------------------------------------------------------------------|-------------------------------------------------------------------|-------------------------------------------------------------------|
| BEGIN;                                                            |                                                                   |                                                                   |
| UPDATE employees <br/>SET birth_date=NOW()<br/>WHERE emp_no=10001 |                                                                   |                                                                   |
|                                                                   | UPDATE employees <br/>SET birth_date=NOW()<br/>WHERE emp_no=10001 |                                                                   |
|                                                                   |                                                                   | UPDATE employees <br/>SET birth_date=NOW()<br/>WHERE emp_no=10001 |

mysql8.0의 경우 `performance_schema.data_locks`에서 확인할 수 있음

## 4.5 MySQL의 격리 수준

여러 트랜잭션이 처리될 때, 특정 트랜잭션이 다른 트랜잭션에서 변경하거나 조회하는 데이터를 볼 수 있도록 허용할지 말지를 결정하는 것

- READ UNCOMMITTED
- READ COMMITTED
- REPEATABLE READ
- SERIALIZABLE

격리 수준에 따라 동시성 문제가 발생하며 아래와 같은 3가지 데이터 부정합 문제가 발생함

|                  | DIRTY READ | NON-REPEATABLE READ | PHANTOM READ        |
|------------------|------------|---------------------|---------------------|
| READ UNCOMMITTED | 발생         | 발생                  | 발생                  |
| READ COMMITTED   | 발생하지 않음    | 발생                  | 발생                  |
| REPEATABLE READ  | 발생하지 않음    | 발생하지 않음             | 발생(InnoDB는 발생하지 않음) |
| SERIALIZABLE     | 발생하지 않음    | 발생하지 않음             | 발생하지 않음             |

### 4.5.1 READ UNCOMMITTED

- 각 트랜잭션에서의 변경 내용이 COMMIT이나 ROLLBACK 여부에 상관 없이 다른 트랜잭션에서 보여짐
- DIRTY READ 발생

### 4.5.2 READ COMMITTED

- 오라클 DBMS의 기본적인 격리 수준
- DIRTY READ 발생하지 않음
- 한 트랜잭션에서 값을 변경하면 해당 테이블에 값이 즉시 기록되고, 이전 값은 undo 영역에 백업됨
- 다른 트랜잭션에서 값을 읽으면 undo 값을 읽음
- 하나의 트랜잭션 내에서 똑같은 SELECT 쿼리를 실행했을 때 항상 같은 결과를 가져와야 된다는 REPEATABLE READ 정합성에 어긋남

### 4.5.3 REPEATABLE READ

- InnoDB의 격리 수준
- NON-REPEATABLE READ 발행하지 않음
- InnoDB는 트랜잭션이 ROLLBACK 될 가능성을 대비해 전 레코드를 undo 공간에 백업해둠
  - 이런 변경 방식을 MVCC(Multi Version Concurrency Control)이라고 함
- undo 영역에 데이터를 백업하기 때문에 트랜잭션 내에서 동일한 SELECT 결과를 보여줄 수 있도록 보장함

### 4.5.4 SERIALIZABLE

- 읽기 작업도 공유 잠금을 획득해야 함