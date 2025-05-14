package com.mikehans.d308vacationplanner.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    public static boolean isValidDate(String date) {
        return date != null && date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    public static boolean isDateRangeValid(String start, String end) {
        try {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);
            return endDate.isAfter(startDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isBefore(String date1, String date2) {
        return LocalDate.parse(date1).isBefore(LocalDate.parse(date2));
    }

    public static boolean isAfter(String date1, String date2) {
        return LocalDate.parse(date1).isAfter(LocalDate.parse(date2));
    }
}

