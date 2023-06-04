package org.example;

import org.example.Dollar;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MoneyReportTest {

    @Test
    @DisplayName("달러에 수를 곱한 결과를 얻어야 함")
    void testMultiplication() throws Exception {
        //given
        Dollar five = Money.dollar(5);

        //when
        Dollar product1 = five.times(2);
        Dollar product2 = five.times(3);

        //then
        assertThat(product1).isEqualTo(Money.dollar(10));
        assertThat(product2).isEqualTo(Money.dollar(15));
    }

    @Test
    @DisplayName("값 객체 동등성 테스트")
    void testEquality() throws Exception {
        assertThat(Money.dollar(5).equals(Money.dollar(5))).isTrue();
        assertThat(Money.dollar(5).equals(Money.dollar(6))).isFalse();
        assertThat(new Franc(5).equals(new Franc(5))).isTrue();
        assertThat(new Franc(5).equals(new Franc(6))).isFalse();
        assertThat(new Franc(5).equals(Money.dollar(5))).isFalse();
    }

    @Test
    @DisplayName("주가에 주식의 수를 곱한 결과를 얻어야 함")
    void testFrancMultiplication() throws Exception {
        //given
        Franc five = new Franc(5);

        //when
        Franc product1 = five.times(2);
        Franc product2 = five.times(3);

        //then
        assertThat(product1).isEqualTo(new Franc(10));
        assertThat(product2).isEqualTo(new Franc(15));
    }

}
