package org.example.v1;

import java.time.Duration;

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
