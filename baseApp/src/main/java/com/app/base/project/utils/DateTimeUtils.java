package com.app.base.project.utils;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {

    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   public static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Date getCurrentDateTime() {
        return new Date();
    }

    public static Date formatDate(String date,DateTimeFormatter formatter){
        LocalDate localDate = LocalDate.parse(date, formatter);
        return java.sql.Date.valueOf(localDate);
    }
    public static Time formatTime(String time,DateTimeFormatter formatter){
        LocalTime formattedTime =   LocalTime.parse(time, formatter);
        return java.sql.Time.valueOf(formattedTime);
    }

    public static String getUtcTime() {
        ZonedDateTime currentUTC = ZonedDateTime.now().withZoneSameInstant(java.time.ZoneOffset.UTC);

        // Format the UTC date and time (optional)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        String formattedUTCTime = currentUTC.format(formatter);

        return formattedUTCTime;
    }


    public static Boolean getUtcTimeDiff_1HR(String dbUTCTimeStr) {
        Print.log("DB_CURRENT_TIME "+dbUTCTimeStr);
        String currentUTCTimeStr = getUtcTime();
        ZonedDateTime currentUTC = ZonedDateTime.parse(currentUTCTimeStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));

        // Parse the DB UTC time
        ZonedDateTime dbUTC = ZonedDateTime.parse(dbUTCTimeStr);

        // Check if current time is greater than 1 hour from the DB time
        boolean isGreaterThan1Hour = dbUTC.plusHours(1).isBefore(currentUTC);

        // Output the comparison result
        if (isGreaterThan1Hour) {
            System.out.println("Current time is more than 1 hour after DB time.");
        } else {
            System.out.println("Current time is not more than 1 hour after DB time.");
        }
        return isGreaterThan1Hour;
    }
}
