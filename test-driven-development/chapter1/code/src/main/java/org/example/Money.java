package org.example;

public class Money {

    protected final int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public Money times(int multiplier) {
        return new Money(amount * multiplier);
    }

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return amount == money.amount &&
                getClass().equals(money.getClass());
    }
}
