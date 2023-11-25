package org.example;

import org.example.v1.Phone;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RegularPhoneTest {

    @Test
    void calculateFee() throws Exception {
        //given
        Phone phone = new Phone(Money.wons(5), Duration.ofSeconds(10));
        phone.call(new Call(LocalDateTime.of(2018, 1, 1, 12, 10, 0),
                            LocalDateTime.of(2018, 1, 1, 12, 11, 0)));
        phone.call(new Call(LocalDateTime.of(2018, 1, 2, 12, 10, 0),
                            LocalDateTime.of(2018, 1, 2, 12, 11, 0)));

        //when
        Money money = phone.calculateFee();

        //then
        assertEquals(Money.wons(60.0), money);
    }
}