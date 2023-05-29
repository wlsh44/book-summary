package org.example.v1;

import java.time.Duration;

public class Movie {
    private final String title;
    private final Duration playTime;
    private final Money price;
    private final DiscountPolicy discountPolicy;

    public Movie(String title, Duration playTime, Money price, DiscountPolicy discountPolicy) {
        this.title = title;
        this.playTime = playTime;
        this.price = price;
        this.discountPolicy = discountPolicy;
    }

    public Money getDiscountedPrice(Screening screening) {
        return price.minus(discountPolicy.calculateDiscountPrice(screening, price));
    }

    public Money getFee() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public Duration getPlayTime() {
        return playTime;
    }
}
