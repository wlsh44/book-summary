package org.example.v1;

public class NoneDiscountPolicy implements DiscountPolicy {
    @Override
    public Money calculateDiscountPrice(Screening screening, Money money) {
        return money;
    }
}
