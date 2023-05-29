package org.example.v1;

public class SequenceCondition implements DiscountCondition {

    private int sequence;

    public SequenceCondition(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public boolean isConditionRight(Screening screening) {
        return this.sequence == screening.getSequence();
    }
}
