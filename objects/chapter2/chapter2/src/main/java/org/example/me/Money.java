package org.example.me;

import java.util.Objects;

public class Money {
    private double amount;

    public Money(double amount) {
        this.amount = amount;
    }

    public Money minus(Money money) {
        return new Money(amount - money.amount);
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Double.compare(money.amount, amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
