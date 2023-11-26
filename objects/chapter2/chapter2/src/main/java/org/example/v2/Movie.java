package org.example.v2;

import java.time.Duration;
import java.time.LocalDateTime;

public class Movie {
    private final String title;
    private final Duration playTime;
    private final Money fee;
    private final DiscountPolicy discountPolicy;

    public Movie(String title, Duration playTime, Money fee, DiscountPolicy discountPolicy) {
        this.title = title;
        this.playTime = playTime;
        this.fee = fee;
        this.discountPolicy = discountPolicy;
    }

    public Money calculateMovieFee(Screening screening) {
        if (screening == null || screening.getStartTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException();
        }
        return fee.minus(discountPolicy.calculateDiscountPrice(screening));
    }

    public Money getFee() {
        return fee;
    }

    public String getTitle() {
        return title;
    }

    public Duration getPlayTime() {
        return playTime;
    }
}
