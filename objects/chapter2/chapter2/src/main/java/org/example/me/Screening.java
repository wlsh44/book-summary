package org.example.me;

import java.time.LocalDateTime;

public class Screening {

    private final Movie movie;
    private final int sequence;
    private final LocalDateTime whenScreened;

    public Screening(Movie movie, int sequence, LocalDateTime whenScreened) {
        this.movie = movie;
        this.sequence = sequence;
        this.whenScreened = whenScreened;
    }

    public Reservation reserve(Customer customer) {
        Money price = movie.getDiscountedPrice(this);
        return new Reservation(this, customer, price);
    }

    public int getSequence() {
        return sequence;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getWhenScreened() {
        return whenScreened;
    }
}
