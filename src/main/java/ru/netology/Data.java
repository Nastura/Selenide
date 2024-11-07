package ru.netology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Data {
    public String formatPlus(long day) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (day > 0 ) {
            LocalDate newDay = today.plusDays(day);
            return newDay.format(formatter);
        } else {
            LocalDate newDay = today.minusDays(day);
            return newDay.format(formatter);
        }
    }
}
