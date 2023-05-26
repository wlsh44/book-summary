## 1-2 무엇이 문제인가

> 모든 소프트웨어 모듈에는 세 가지 목적이 있다.<br>
> 첫 번째 목적은 실행 중에 제대로 동작하는 것이다. 이것은 모듈의 존재 이유라고 할 수 있다.<br>
> 두 번째 목적은 변경을 위해 존재하는 것이다. 대부분의 모듈은 생명주기 동안 변경되기 때문에 간단한 작업만으로도 변경이 가능해야 한다. 변경하기 어려윤 모듈은 제대로 동작하더라도 개선해야 한다.<br>
> 모듈의 세 번째 목적은 코드를 읽는 사람과 의사소통하는 것이다. 모듈은 특별한 훈련 없이도 개발자가 쉽게 읽고 이해할 수 있어야 한다. 읽는 사람과 의사소통할 수 없는 모듈은 개선해야 한다.

=> **모듈은 제대로 실행돼야 하고, 변경에 용이해야 하며, 이해하기 쉬워야 한다.**

### 예상을 빗나가는 코드

```java
public class Theater {

    private TicketSeller ticketSeller;

    public Theater(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    public void enter(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = ticketSeller.getTicketOffice().getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketSeller.getTicketOffice().plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}

```

```text
코드 흐름

소극장은 관람객의 가방을 열어 그 안에 초대장이 들어 있는지 살펴봄
가방 안에 초대장이 들어 있으면 판매원은 매표소에 보관돼 있는 티켓을 관람객의 가방 안으로 옮김
가방 안에 초대장이 들어 있지 않다면 관람객의 가방에서 티켓 금액만큼의 현금을 꺼내 매표소에 적립한 후에 매표소에 보관돼 있는 티켓을 관람객의 가방 안으로 옮김
```

**문제점 1.** 관람객과 판매원이 소극장의 통제를 받는 수동적인 존재라는 점
- 관람객의 경우 소극장이라는 제 3자가 관람객의 가방을 마음대로 열어 봄
- 판매원의 경우 소극장이라는 제 3자가 매표소의 보관 중인 티켓과 현금에 마음대로 접근함
- 이해 가능한 코드란 우리 예상에 크게 벗어나지 않는 코드
  => **현재 코드는 예상에서 벗어남**, 협력이 이뤄지지 않고 있음
    
**문제점 2.** 세부적인 내용들을 한꺼번에 기억하고 있어야 함
- Audience가 Bag을 가지고 있음
- Bag 안에는 현금과 티켓이 들어 있음
- TicketSeller가 TicketOffice에서 티켓을 판매함
- 등

### 변경에 취약한 코드

**문제점 3.** 의존성이 너무 높기 때문에(결합도가 높음) 변경에 취약함
- 가방을 들고 있지 않다면?
- 현금이 아니라 카드를 이용해서 결제를 한다면?

가방을 들고 있지 않는 경우
- Audience의 Bag 제거
- Theater의 enter 메서드 수정

**의존성**
- 의존성은 변경과 관련돼 있음
- 변경에 대한 영향을 암시 -> 의존성이 높으면 변경의 영향 커짐, 낮으면 영향 줄어듬
- 어떤 객체가 변경될 때 그 객체에게 의존하는 다른 객체도 함께 변경될 수 있다는 사실이 내포돼 있음
- **의존성을 없애는 것이 정답이 아니라 서로 의존하면서 협력하는 객체들의 공동체를 구축하는 것이 객체지향의 목표**

**결합도(Coupling)**
- 객체들의 의존성이 과한 경우 -> 결합도 높음
- 객체들의 의존성이 낮은 경우 -> 결합도 낮음
- 결합도를 낮춰 변경에 용이한 설계를 만들어야 함

## 1-3 설계 개선하기

예제 코드를 이해하기 어려운 이유 -> Theater가 관람객의 가방과 판매원의 매표소에 직접 접근하기 때문<br>
=> Theater가 Audience와 TicketSeller에 결합된다는 것을 의미

Theater가 Audience와 TicketSeller에 관해 자세히 알지 못하도록 캡슐화를 통해 해결할 수 있음<br>
Audience와 TicketSeller를 **자율적인 존재**로 만들어야 함

### 자율성을 높이자

**첫 번째 단계**

- Theater의 enter 메서드에서 TicketOffice에 접근하는 모든 코드를 TicketSeller 내부로 숨기기

```java
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

//    public TicketOffice getTicketOffice() {
//        return ticketOffice;
//    }

    public void sellTo(Audience audience) {
        if (audience.getBag().hasInvitation()) {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().setTicket(ticket);
        } else {
            Ticket ticket = ticketOffice.getTicket();
            audience.getBag().minusAmount(ticket.getFee());
            ticketOffice.plusAmount(ticket.getFee());
            audience.getBag().setTicket(ticket);
        }
    }
}
```

변경된 점
- Theater가 더이상 TicketOffice에 접근할 수 없음
- TicketSeller는 ticketOffice에서 티켓을 꺼내거나 판매 요금을 적립하는 일을 스스로 수행할 수밖에 없음

**캡슐화(encapsulation)**
- 개념적이나 물리적으로 객체 내부의 세부적인 사항을 감추는 것
- 변경하기 쉬운 객체를 만드는 것
- 객체 내부로의 접근을 제한하면 객체와 객체 사이의 결합도를 낮출 수 있음 -> 설계를 좀 더 쉽게 변경할 수 있음

```java
public class Theater {

  private TicketSeller ticketSeller;

  public Theater(TicketSeller ticketSeller) {
    this.ticketSeller = ticketSeller;
  }

  public void enter(Audience audience) {
    ticketSeller.sellTo(audience);
  }
}
```

- Theater는 TicketSeller 인터페이스에만 의존
- TicketSeller가 내부에 TicketOffice 인스턴스를 포함하고 있다는 사실은 구현(implememtation)의 영역에 속함

**두 번째 단계**

- Audience 캡슐화를 통해 TicketSeller에서 Bag의 의존성 제거

```java
public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        Ticket ticket = ticketOffice.getTicket();
        Long amount = audience.buyTicket(ticket);
        ticketOffice.plusAmount(amount);
//        if (audience.hasInvitation()) {
//            Ticket ticket = ticketOffice.getTicket();
//            audience.putTicket(ticket);
//        } else {
//            Ticket ticket = ticketOffice.getTicket();
//            audience.minusAmount(ticket.getFee());
//            ticketOffice.plusAmount(ticket.getFee());
//            audience.putTicket(ticket);
//        }
    }
}

public class Audience {

  private Bag bag;

  public Audience(Bag bag) {
    this.bag = bag;
  }

//    public Bag getBag() {
//        return bag;
//    }

  public Long buyTicket(Ticket ticket) {
    if (bag.hasInvitation()) {
      bag.setTicket(ticket);
      return 0L;
    } else {
      bag.setTicket(ticket);
      bag.minusAmount(ticket.getFee());
      return ticket.getFee();
    }
  }
}

```

변경된 점
- Audience는 자신의 가방 안에 초대장이 들어있는지를 스스로 확인함
- 외부에서는 Audience가 Bag을 소유하고 있다는 사실을 알 필요가 없음

### 무엇이 개선되었는가

- 필요한 기능을 오류 없이 수행해야 함 ✅
- 코드가 우리의 예상과 정확하게 일치해야 함 ✅ 
- 설계가 변경에 용이해야 함 ✅ 

### 어떻게 한 것인가

- **캡슐화를 통해 객체가 자기 자신의 문제를 스스로 해결하도록 코드를 변경**
  - 결합도가 낮아지고 객체의 자율성을 높이는 방향으로 설계를 개선

### 결합도와 응집도

**응집도(cohesion)**
- 밀접하게 연관된 작업만을 수행하고 연관성 없는 작업은 다른 객체에게 위임하는 것
- 위를 잘 지킬 수록 응집도가 높은 객체
- 응집도를 높이기 위해서는 스스로 자신의 데이터를 처리해야 하는 자율적인 존재여야 함

### 절차지향과 객체지향

수정하기 전 코드
- Theater의 enter 메서드 안에서 Audience와 TicketSeller로부터 Bag과 TicketOffice를 가져와 관람객을 입장시키는 절차를 구현
- enter 메서드는 모든 객체에 대한 정보를 알고 있어야 했음
```text
이 관점에서 enter 메서드는 프로세스(process)이며, Audience, TicketSeller, Bag, TicketOffice는 데이터(data)
```

절차지향 문제점
- 의존성 높은 코드
- 우리의 예상에서 벗어나 코드를 읽는 사람과의 원활한 소통 x
- 데이터의 변경으로 인한 영향을 지역적으로 고립시키기 어려움
- 변경에 취약

**객체지향 프로그래밍이란?**
- 자신의 데이터를 스스로 처리하도록 이동시켜, 데이터와 프로세스가 동일한 모듈 내부에 위치하도록 프로그래밍하는 방식
- 변경에 유연

### 책임의 이동

두 방식의 근본적인 차이를 만드는 근간은 바로 **책임의 이동(shift of responsibility)**

- v1 코드에서는 책임이 Theater에 집중되어 있었음

> 객체가 어떤 데이터를 가지느냐보다는 객체에 어떤 책임을 할당할 것이냐에 초점을 맞춰야 한다.


**결론**
- 설계를 어럽게 만드는 것은 **의존성**
- 의존성을 제거하여 객체 사이의 **결합도**를 낮춰야 함
- 결합도를 낮추기 위해 **캡슐화**를 함
- 캡슐화를 통해 객체의 **자율성**을 높이고 **응집도** 높은 객체들의 공동체를 만들 수 있음

### 더 개선할 수 있다

- Bag을 자율적인 존재로 만들어보자

```java
public class Bag {

    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    public Bag(Long amount) {
        this(amount, null);
    }

    public Bag(Long amount, Invitation invitation) {
        this.amount = amount;
        this.invitation = invitation;
    }

    private boolean hasInvitation() {
        return invitation != null;
    }

    public Long hold(Ticket ticket) {
        if (hasInvitation()) {
            setTicket(ticket);
            return 0L;
        } else {
            setTicket(ticket);
            minusAmount(ticket);
            return ticket.getFee();
        }
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private void minusAmount(Ticket ticket) {
        amount -= ticket.getFee();
    }
}

public class Audience {

  private Bag bag;

  public Audience(Bag bag) {
    this.bag = bag;
  }

  public Long buyTicket(Ticket ticket) {
    return bag.hold(ticket);
  }
}
```

- TicketOffice를 자율적인 존재로 만들어보자

```java
public class TicketOffice {
  private Long amount;
  private List<Ticket> tickets = new ArrayList<>();

  public TicketOffice(Long amount, Ticket... tickets) {
    this.amount = amount;
    this.tickets.addAll(Arrays.asList(tickets));
  }

  private Ticket getTicket() {
    return tickets.remove(0);
  }

  public void sellTicketTo(Audience audience) {
    plusAmount(audience.buyTicket(getTicket()));
  }

  private void plusAmount(Long amount) {
    this.amount += amount;
  }
}

public class TicketSeller {
    private TicketOffice ticketOffice;

    public TicketSeller(TicketOffice ticketOffice) {
        this.ticketOffice = ticketOffice;
    }

    public void sellTo(Audience audience) {
        ticketOffice.sellTicketTo(audience);
    }
}
```

문제점
- TicketOffice와 Audience 사이의 의존성이 추가됨

> 첫째. 어떤 기능을 설계하는 방법은 한 가지 이상일 수 있다.<br>
> 둘째. 동일한 기능을 한 가지 이상의 방법으로 설계할 수 있기 때문에 결국 설계는 트레이드오프의 산물이다.<br>
> 어떤 경우에도 모든 사람들을 만족시킬 수 있는 설계를 만들 수는 없다.

### 그래, 거짓말이다!

- TicketSeller와 Audience는 우리가 세상을 바라보는 직관과 일치하게 동작함
- 하지만 Theater와 Bag, TicketOffice는 실세계에서 자율적인 존재가 아님

**객체 지향의 세계에서는 모든 객체가 능동적이고 자율적인 존재로 바뀜**
- 의인화(anthropomorphism)

```text
이해하기 쉬운 코드란 실세계의 생물처럼 스스로 생각하고 행동하도록 소프트웨어를 설계하는 것이 아닌,
소프트웨어를 구성하는 모든 객체들이 자율적으로 행동하는 설계를 가리킨다.
```