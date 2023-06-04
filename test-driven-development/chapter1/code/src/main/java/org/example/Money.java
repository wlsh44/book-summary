package org.example;

public abstract class Money {

    protected final int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    public static Dollar dollar(int amount) {
        return new Dollar(amount);
    }

    public abstract Money times(int multiplier);

    @Override
    public boolean equals(Object obj) {
        Money money = (Money) obj;
        return amount == money.amount &&
                getClass().equals(money.getClass());
    }
}
