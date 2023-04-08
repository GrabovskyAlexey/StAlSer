package ru.stalser.framework.helpers;

import java.time.LocalDateTime;

public class ParseHelper {

    public static LocalDateTime parseDateTimeFromDBToLocalDateTime(String dateData) {

        if (dateData.length() > 19) {
            dateData = dateData.substring(0, 19);
        }
        dateData = dateData.replace(" ", "T");

        return LocalDateTime.parse(dateData);
    }
}
