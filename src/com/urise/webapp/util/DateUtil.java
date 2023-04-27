package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "По настоящее время" : date.toString();
    }

    public static LocalDate parse(String date) {
        if (date == null || date.trim().equals("") || "По настоящее время".equals(date)) return NOW;
        return LocalDate.parse(date, DATE_FORMATTER);
    }
}
