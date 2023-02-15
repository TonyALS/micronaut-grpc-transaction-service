package br.com.content4devs.functions;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;

public class DateTimeConverter {
    public static final DateTimeFormatter ISO_DATE_PATTERN = DateTimeFormatter
            .ofPattern("yyyy-MM-dd");

    public static final Function<String, LocalDate> stringToLocalDate =
            date -> LocalDate.parse(date, ISO_DATE_PATTERN);

    public static final Function<LocalDate, String> localDateToString =
            date -> date.format(ISO_DATE_PATTERN);
}
