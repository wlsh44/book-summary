package org.example;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyReportTest {

    @Test
    @DisplayName("달러에 수를 곱한 결과를 얻어야 함")
    void testMultiplication() throws Exception {
        //given
        Money five = Money.dollar(5);

        //when
        Money product1 = five.times(2);
        Money product2 = five.times(3);

        //then
        assertThat(product1).isEqualTo(Money.dollar(10));
        assertThat(product2).isEqualTo(Money.dollar(15));
    }

    @Test
    @DisplayName("값 객체 동등성 테스트")
    void testEquality() throws Exception {
        assertThat(Money.dollar(5).equals(Money.dollar(5))).isTrue();
        assertThat(Money.dollar(5).equals(Money.dollar(6))).isFalse();
        assertThat(Money.franc(5).equals(Money.dollar(5))).isFalse();
    }

    @Test
    @DisplayName("주가에 주식의 수를 곱한 결과를 얻어야 함")
    void testFrancMultiplication() throws Exception {
        //given
        Money five = Money.franc(5);

        //when
        Money product1 = five.times(2);
        Money product2 = five.times(3);

        //then
        assertThat(product1).isEqualTo(Money.franc(10));
        assertThat(product2).isEqualTo(Money.franc(15));
    }

    @Test
    @DisplayName("통화 테스트")
    void testCurrency() throws Exception {
        //given
        Money dollar = Money.dollar(1);
        Money franc = Money.franc(1);

        //when
        String dollarCurrency = dollar.currency();
        String francCurrency = franc.currency();

        //then
        assertThat(dollarCurrency).isEqualTo("USD");
        assertThat(francCurrency).isEqualTo("CHF");
    }

    @Test
    @DisplayName("다른 클래스 동등성 테스트")
    void testDifferenceClassEquality() throws Exception {
        //given
        Money money = new Money(10, "CHF");
        Money franc = Money.franc(10);

        //when

        //then
        assertThat(money).isEqualTo(franc);
    }
}
