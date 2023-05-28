package org.example;

import org.example.me.AmountDiscountPolicy;
import org.example.me.DiscountCondition;
import org.example.me.Money;
import org.example.me.Movie;
import org.example.me.NoneDiscountPolicy;
import org.example.me.PercentDiscountPolicy;
import org.example.me.PeriodCondition;
import org.example.me.Screening;
import org.example.me.SequenceCondition;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;

@DisplayName("Movie Class V2")
class Chapter2Tests {

    SequenceCondition 첫_번째_순서_할인조건 = new SequenceCondition(1);
    SequenceCondition 두_번째_순서_할인조건 = new SequenceCondition(2);
    SequenceCondition 열_번째_순서_할인조건 = new SequenceCondition(10);

    DiscountCondition 화요일_14시_16시_할인조건 = new PeriodCondition(DayOfWeek.TUESDAY, LocalTime.of(14, 0), LocalTime.of(16, 59));
    DiscountCondition 목요일_10시_14시_할인조건 = new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(13, 59));
    DiscountCondition 월요일_10시_12시_할인조건 = new PeriodCondition(DayOfWeek.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 59));
    DiscountCondition 목요일_10시_21시_할인조건 = new PeriodCondition(DayOfWeek.THURSDAY, LocalTime.of(10, 0), LocalTime.of(20, 59));


    private final Movie titanic = new Movie("타이타닉",
                Duration.ofMinutes(180),
                new Money(11_000),
                new PercentDiscountPolicy(0.1,
                        List.of(
                                두_번째_순서_할인조건,
                                화요일_14시_16시_할인조건,
                                목요일_10시_14시_할인조건
                        )
                ));

    private final BigDecimal discountFee = new BigDecimal(800);
    private final Movie avatar = new Movie("아바타",
            Duration.ofMinutes(120),
            new Money(10_000),
            new AmountDiscountPolicy(
                    new Money(discountFee.doubleValue()),
                    List.of(
                            첫_번째_순서_할인조건,
                            열_번째_순서_할인조건,
                            월요일_10시_12시_할인조건,
                            목요일_10시_21시_할인조건
                    )
            ));

    private final Movie starWars = new Movie("스타워즈",
        Duration.ofMinutes(210),
        new Money(10_000),
        new NoneDiscountPolicy()
    );

    @Nested
    @DisplayName("Avatar Class")
    class Avatar {

        @Test
        void discounted_cost() {
            final LocalDate monday = LocalDate.of(2023, 4, 17);
            List<Screening> discountedScreeningList = List.of(
                new Screening(avatar, 1, LocalDateTime.of( 2023, 4,19, 8, 59)),
                new Screening(avatar, 10, LocalDateTime.of( 2023, 4,19, 9, 30)),
                new Screening(avatar, 9999, monday.atTime(11, 59)),
                new Screening(avatar, 9999, monday.plusDays(3).atTime(20, 59))
            );

            for (Screening discountingScreening : discountedScreeningList) {
                final Money calculated = avatar.getDiscountedPrice(discountingScreening);

                Assertions.assertEquals(avatar.getPrice().minus(new Money(discountFee.doubleValue())), calculated);
            }
        }

        @Test
        void not_discounted_cost() {
            List<Screening> discountedScreeningList = List.of(
                new Screening(avatar, 2, LocalDateTime.of( 2023, 4,19, 8, 59))
            );

            for (Screening discountingScreening : discountedScreeningList) {
                final Money calculated = avatar.getDiscountedPrice(discountingScreening);

                Assertions.assertEquals(avatar.getPrice(), calculated);
            }
        }

    }
}