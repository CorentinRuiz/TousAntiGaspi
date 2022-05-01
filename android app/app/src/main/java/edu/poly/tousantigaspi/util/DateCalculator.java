package edu.poly.tousantigaspi.util;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateCalculator {

    LocalDate todayDate;

    public DateCalculator() {
        this.todayDate = LocalDate.now();
    }

    public String calculateDaysRemaining(String date, DateTimeFormatter formatter){
        LocalDate dlc = LocalDate.parse(date,formatter);
        long daysRemaining =  DAYS.between(todayDate,dlc);

        if(daysRemaining > 1){
            return (int) daysRemaining + " jours ";
        }
        return (int) daysRemaining + " jour ";
    }
}
