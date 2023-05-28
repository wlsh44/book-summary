package org.example.me;

public interface DiscountPolicy {
    Money calculateDiscountPrice(Screening screening, Money money);
}