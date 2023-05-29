package org.example.v1;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class PeriodCondition implements DiscountCondition {

    private DayOfWeek dayOfWeek;
    private LocalTime startDateTime;
    private LocalTime endDateTime;

    public PeriodCondition(DayOfWeek dayOfWeek, LocalTime startDateTime, LocalTime endDateTime) {
        this.dayOfWeek = dayOfWeek;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @Override
    public boolean isConditionRight(Screening screening) {
        LocalDateTime whenScreened = screening.getStartTime();
        return whenScreened.getDayOfWeek().equals(dayOfWeek) && isBetweenPeriod(whenScreened.toLocalTime());
    }

    private boolean isBetweenPeriod(LocalTime whenScreened) {
        return (whenScreened.isAfter(startDateTime) && whenScreened.isBefore(endDateTime)) ||
                (whenScreened.equals(startDateTime) || whenScreened.equals(endDateTime));
    }
}
