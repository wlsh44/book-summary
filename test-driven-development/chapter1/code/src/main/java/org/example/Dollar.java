package org.example;

public class Dollar extends Money {

    public Dollar(int amount, String currency) {
        super(amount, currency);
    }

    public Dollar times(int multiplier) {
        return Money.dollar(amount * multiplier);
    }

    @Override
    public String currency() {
        return currency;
    }
}
