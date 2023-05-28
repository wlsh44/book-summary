package org.example.me;

import java.util.List;

public class AmountDiscountPolicy implements DiscountPolicy {

    private final Money discountMoney;
    private final List<DiscountCondition> discountConditions;

    public AmountDiscountPolicy(Money discountMoney, List<DiscountCondition> discountConditions) {
        this.discountMoney = discountMoney;
        this.discountConditions = discountConditions;
    }

    @Override
    public Money calculateDiscountPrice(Screening screening, Money money) {
        long discountCount = discountConditions.stream()
                .filter(discountCondition -> discountCondition.isConditionRight(screening))
                .count();
        return new Money(discountMoney.getAmount() * discountCount);
    }
}
