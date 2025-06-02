package ee.bcs.dosesyncback.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static String localDateToString(LocalDate localDate) {
        return localDate != null ? localDate.format(DATE_FORMATTER) : null;
    }

    public static LocalDate stringToLocalDate(String date) {
        return date != null ? LocalDate.parse(date, DATE_FORMATTER) : null;
    }

    public static String localTimeToString(LocalTime localTime) {
        return localTime != null ? localTime.format(TIME_FORMATTER) : null;
    }

    public static LocalTime stringToLocalTime(String time) {
        return time != null ? LocalTime.parse(time, TIME_FORMATTER) : null;
    }
}
