package com.example.newsandroidproject.common;

import android.os.Build;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class DateParser {

    public static String formatToISO8601(Date date) {
        SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX");
        isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return isoFormat.format(date);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String timeSince(Date date) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inputDate = date.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        Duration duration = Duration.between(inputDate, now);
        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return "Vừa xong";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            return minutes + " phút trước";
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            return hours + " giờ trước";
        } else {
            LocalDate today = LocalDate.now();
            LocalDate inputLocalDate = inputDate.toLocalDate();

            long daysBetween = ChronoUnit.DAYS.between(inputLocalDate, today);

            if (daysBetween == 1) {
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
                return "Hôm qua lúc " + timeFormatter.format(inputDate);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                return formatter.format(inputDate);
            }
        }
    }
}
