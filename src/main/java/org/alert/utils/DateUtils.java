package org.alert.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

import static org.alert.domain.constants.AppConstant.default_date_pattern;
import static org.alert.domain.constants.AppConstant.default_date_time_pattern;
import static org.alert.domain.constants.AppConstant.locale;

public class DateUtils {
    public static LocalDate parseDate(String str, String pattern, Locale locale) {
        return LocalDate.parse(str, DateTimeFormatter.ofPattern(pattern, locale));
    }

    public static LocalDate parseDate(String str, String pattern) {
        return parseDate(str, pattern, locale);
    }

    public static LocalDate parseDate(String str) {
        return parseDate(str, default_date_pattern);
    }

    public static LocalDate parseDateOr(String str, LocalDate ifDefault) {
        try {
            return parseDate(str);
        } catch (Exception e) {
            return ifDefault;
        }
    }

    public static LocalDate parseDateOrNull(String str) {
        return parseDateOr(str, null);
    }

    public static LocalDateTime from(long timestamp) {
        if (timestamp == 0)
            return null;
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), TimeZone
                .getDefault().toZoneId());
    }

    public static String format(LocalDateTime dateTime, String pattern, Locale locale) {
        return dateTime.format(DateTimeFormatter.ofPattern(pattern, locale));
    }

    public static String format(LocalDateTime dateTime, String pattern) {
        return format(dateTime, pattern,  locale);
    }

    public static String format(LocalDateTime dateTime) {
        return format(dateTime, default_date_time_pattern, locale);
    }

    public static String formatOrEmptyString(LocalDateTime dateTime) {
        try {
            return format(dateTime, default_date_time_pattern, locale);
        } catch (Exception e) {
            return "";
        }
    }

}
