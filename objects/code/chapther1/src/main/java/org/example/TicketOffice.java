package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketOffice {
    private Long amount;
    private List<Ticket> tickets = new ArrayList<>();

    public TicketOffice(Long amount, Ticket... tickets) {
        this.amount = amount;
        this.tickets.addAll(Arrays.asList(tickets));
    }

    public Ticket getTicket() {
        return tickets.get(0);
    }

    public void minusTicket(Long amount) {
        this.amount -= amount;
    }

    public void plusTicket(Long amount) {
        this.amount += amount;
    }
}
