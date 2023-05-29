package org.example.v1;

public interface DiscountPolicy {
    Money calculateDiscountPrice(Screening screening, Money money);
}