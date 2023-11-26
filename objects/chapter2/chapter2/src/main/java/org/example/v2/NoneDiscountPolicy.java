package org.example.v2;

import java.util.List;

public class NoneDiscountPolicy extends DiscountPolicy {

    public NoneDiscountPolicy(List<DiscountCondition> conditions) {
        super(conditions);
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
