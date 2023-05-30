package org.example.v1;

import java.util.List;

public class PercentDiscountPolicy extends DiscountPolicy {

    private final double percent;

    public PercentDiscountPolicy(double percent, List<DiscountCondition> discountConditions) {
        super(discountConditions);
        this.percent = percent;
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return screening.getMovieFee().times(percent);
    }
}
