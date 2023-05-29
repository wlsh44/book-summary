package org.example.v1;

public class Reservation {

    private final Customer customer;
    private final Screening screening;
    private final Money price;
    private final int audienceCount;

    public Reservation(Customer customer, Screening screening, Money price, int audienceCount) {
        this.customer = customer;
        this.screening = screening;
        this.price = price;
        this.audienceCount = audienceCount;
    }
}
