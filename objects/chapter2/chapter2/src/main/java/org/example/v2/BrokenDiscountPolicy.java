package org.example.v2;

import java.time.LocalTime;
import java.util.List;

public class BrokenDiscountPolicy extends DiscountPolicy {

    public BrokenDiscountPolicy(List<DiscountCondition> conditions) {
        super(conditions);
    }

    @Override
    public Money calculateDiscountPrice(Screening screening) {
//        checkPrecondition(screening);             생략 가능
//        checkStrongerPrecondition(screening);     서브 타입에 더 강력한 사전 조건 정의 불가
        checkWeakerPrecondition(screening);         //더 약한 사전 조건 정의 가능
        Money amount = screening.getMovieFee();

        checkPostcondition(amount);                 //서브 타입에서 생략 불가
        checkStrongerPostCondition(amount);         //서브 타입에서 더 강한 사후 조건 정의 가능
//        checkWeakerPostCondition(amount);         서브 타입에서 더 약한 사후 조건 정의 불가
        return amount;
    }

    private void checkWeakerPostCondition(Money amount) {
        assert amount != null;
    }

    private void checkStrongerPostCondition(Money amount) {
        assert amount.isGreaterThanOrEqual(Money.wons(1000));
    }

    private void checkWeakerPrecondition(Screening screening) {
        assert screening != null;
    }


    private void checkStrongerPrecondition(Screening screening) {
        assert screening.getEndTime().toLocalTime().isBefore(LocalTime.MIDNIGHT);
    }

    @Override
    protected Money getDiscountAmount(Screening screening) {
        return Money.ZERO;
    }
}
