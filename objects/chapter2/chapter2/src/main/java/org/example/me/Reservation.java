package org.example.me;

public class Reservation {

    private Screening screening;
    private Customer customer;
    private Money price;

    public Reservation(Screening screening, Customer customer, Money price) {
        this.screening = screening;
        this.customer = customer;
        this.price = price;
    }
}
