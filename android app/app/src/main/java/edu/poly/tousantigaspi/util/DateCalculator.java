package edu.poly.tousantigaspi.util;

import static java.time.temporal.ChronoUnit.DAYS;

import android.content.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import edu.poly.tousantigaspi.R;

public class DateCalculator {

    LocalDate todayDate;

    public DateCalculator() {
        this.todayDate = LocalDate.now();
    }

    public String calculateDaysRemaining(Context context, String date, DateTimeFormatter formatter){
        LocalDate dlc = LocalDate.parse(date,formatter);
        long daysRemaining =  DAYS.between(todayDate,dlc);

        if(daysRemaining > 1){
            return (int) daysRemaining + " " + context.getResources().getString(R.string.days);
        }
        return (int) daysRemaining + " " + context.getResources().getString(R.string.day);
    }
}
