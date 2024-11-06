package ru.netology;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Data {

    private long plusDays;
    private long minusDays;

    public Data(long plusDay, long minusDays) {
        this.plusDays = plusDay;
        this.minusDays = minusDays;
    }

    public String formatPlus() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate newDay = today.plusDays(this.plusDays);
        String actualDay = newDay.format(formatter);
        return actualDay;
    }

    public String formatMinus() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate newDay = today.minusDays(this.minusDays);
        String actualDay = newDay.format(formatter);
        return actualDay;
    }
}
