package org.example.v2;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class DiscountPolicy {
    private List<DiscountCondition> conditions = new ArrayList<>();

    public DiscountPolicy(List<DiscountCondition> conditions) {
        this.conditions = conditions;
    }

    public Money calculateDiscountPrice(Screening screening) {
        checkPrecondition(screening);

        Money amount = Money.ZERO;
        for (DiscountCondition condition : conditions) {
            if (condition.isSatisfiedBy(screening)) {
                amount = getDiscountAmount(screening);
                checkPostcondition(amount);
            }
        }

        amount = screening.getMovieFee();
        checkPostcondition(amount);
        return amount;
    }

    protected void checkPostcondition(Money amount) {
        assert amount != null && amount.isGreaterThanOrEqual(Money.ZERO);
    }

    protected void checkPrecondition(Screening screening) {
        assert screening != null && screening.getStartTime().isAfter(LocalDateTime.now());
    }

    abstract protected Money getDiscountAmount(Screening screening);
}