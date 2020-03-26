package org.basecampcodingacademy.reflections.exc;

import java.time.LocalDate;

public class OneReflectionPerDay extends Exception {
    public LocalDate date;
    public OneReflectionPerDay(LocalDate date) {
        this.date = date;
    }
}
