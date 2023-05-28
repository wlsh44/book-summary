package org.example.me;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

public class ClientDemo {
    public static void main(String[] args) {
        Movie avatar = new Movie("아바타",
                Duration.ofMinutes(120),
                new Money(10_000),
                new AmountDiscountPolicy(new Money(800),
                        List.of(
                                new SequenceCondition(1),
                                new SequenceCondition(10),
                                new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59)),
                                new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59))
                        )
                ));

        Movie titanic = new Movie("타이타닉",
                Duration.ofMinutes(180),
                new Money(11_000),
                new PercentDiscountPolicy(0.1,
                        List.of(
                                new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59)),
                                new SequenceCondition(2),
                                new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59))
                        )
                ));

        Movie starWars = new Movie("스타워즈",
                Duration.ofMinutes(210),
                new Money(10_000),
                new NoneDiscountPolicy()
        );
    }
}
