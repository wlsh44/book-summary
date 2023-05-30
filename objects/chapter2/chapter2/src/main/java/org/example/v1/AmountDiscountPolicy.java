package org.example.v1;

import java.util.List;

public class AmountDiscountPolicy extends DiscountPolicy {

    private final Money discountMoney;

    public AmountDiscountPolicy(Money discountMoney, List<DiscountCondition> discountConditions) {
        super(discountConditions);
        this.discountMoney = discountMoney;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return discountMoney;
    }
}
