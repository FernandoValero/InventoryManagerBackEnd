package ar.com.manager.inventory.util;

import ar.com.manager.inventory.exception.ValidationException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Util {

    /**
     * Method to convert a string in dd/MM/yyyy HH:mm:ss format to LocalDateTime
     */
    public static LocalDateTime stringToLocalDateTime(String dateTime) {
        if (dateTime == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new ValidationException("The date provided does not comply with the format dd/MM/yyyy HH:mm:ss");
        }
    }

    /**
     * Method to convert a LocalDateTime to a string in dd/MM/yyyy HH:mm:ss format
     */
    public static String localDateTimeToString(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }


}
