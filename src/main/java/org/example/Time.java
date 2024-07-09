package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Time {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public LocalDateTime convertStringToDAteTime(String stringDateTime){
        StringBuilder strTime = new StringBuilder(stringDateTime);
        strTime.replace(10, 11, " ");
        return LocalDateTime.parse(strTime, formatter);
    }
}
