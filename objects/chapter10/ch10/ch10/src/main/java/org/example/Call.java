package org.example;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Duration;
import java.time.LocalDateTime;

@AllArgsConstructor
public class Call {

    @Getter
    private LocalDateTime from;
    private LocalDateTime to;


    public Duration getDuration() {
        return Duration.between(from, to);
    }

}
