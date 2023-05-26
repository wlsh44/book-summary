package org.example.v3;

public class Bag {

    private Long amount;
    private Invitation invitation;
    private Ticket ticket;

    public Bag(Long amount) {
        this(amount, null);
    }

    public Bag(Long amount, Invitation invitation) {
        this.amount = amount;
        this.invitation = invitation;
    }

    private boolean hasInvitation() {
        return invitation != null;
    }

    public Long hold(Ticket ticket) {
        if (hasInvitation()) {
            setTicket(ticket);
            return 0L;
        } else {
            setTicket(ticket);
            minusAmount(ticket);
            return ticket.getFee();
        }
    }

    private void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    private void minusAmount(Ticket ticket) {
        amount -= ticket.getFee();
    }
}
