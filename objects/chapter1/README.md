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
            ticketSeller.getTicketOffice().plusTicket(ticket.getFee());
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
