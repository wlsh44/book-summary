package org.example.me;

public class NoneDiscountPolicy implements DiscountPolicy {
    @Override
    public Money calculateDiscountPrice(Screening screening, Money money) {
        return money;
    }
}
