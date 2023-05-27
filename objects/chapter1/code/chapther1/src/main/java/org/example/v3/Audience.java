package org.example.v3;

public class Audience {

    private Bag bag;

    public Audience(Bag bag) {
        this.bag = bag;
    }

    public Long buyTicket(Ticket ticket) {
        return bag.hold(ticket);
    }
}
