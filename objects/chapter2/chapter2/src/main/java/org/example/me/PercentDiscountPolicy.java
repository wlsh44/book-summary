package org.example.me;

import java.util.List;

public class PercentDiscountPolicy implements DiscountPolicy {

    private final double percent;
    private final List<DiscountCondition> discountConditions;

    public PercentDiscountPolicy(double percent, List<DiscountCondition> discountConditions) {
        this.percent = percent;
        this.discountConditions = discountConditions;
    }

    @Override
    public Money calculateDiscountPrice(Screening screening, Money money) {
        long discountCount = discountConditions.stream()
                .filter(discountCondition -> discountCondition.isConditionRight(screening))
                .count();
        return new Money(money.getAmount() * percent * discountCount);
    }
}
