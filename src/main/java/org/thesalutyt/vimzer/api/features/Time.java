package org.thesalutyt.vimzer.api.features;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    protected double milliSeconds;
    protected double seconds;
    protected double minutes;
    protected double hours;
    protected double days;

    public Time(String time) {
        String[] parts = time.split("\\.");
        double amount = Double.parseDouble(parts[0]);
        String unit = parts[1];

        switch (unit) {
            case "ms" -> {
                this.milliSeconds = amount;
                this.seconds = amount / 1000;
                this.minutes = seconds / 60;
                this.hours = minutes / 60;
            }
            case "s", "sec" -> {
                this.milliSeconds = amount * 1000;
                this.seconds = amount;
                this.minutes = seconds / 60;
                this.hours = minutes / 60;
            }
            case "m", "min" -> {
                this.milliSeconds = amount * 60000;
                this.seconds = amount * 60;
                this.minutes = amount;
                this.hours = minutes / 60;
            }
            case "h", "hour" -> {
                this.milliSeconds = amount * 3600000;
                this.seconds = amount * 3600;
                this.minutes = amount * 60;
                this.hours = amount;
            }
            case "d", "day" -> {
                this.milliSeconds = amount * 86400000;
                this.seconds = amount * 86400;
                this.minutes = amount * 1440;
                this.hours = amount * 24;
            }
            default -> throw new RuntimeException("Invalid time unit: " + unit);
        }
    }

    public Time(double milliSeconds) {
        this.milliSeconds = milliSeconds;
        this.seconds = milliSeconds / 1000;
        this.minutes = seconds / 60;
        this.hours = minutes / 60;
    }

    public Time milliSeconds(Double milliSeconds) {
        this.milliSeconds = milliSeconds;
        this.seconds = milliSeconds / 1000;
        this.minutes = seconds / 60;
        this.hours = minutes / 60;
        return this;
    }

    public Time seconds(Double seconds) {
        this.seconds = seconds;
        this.milliSeconds = seconds * 1000;
        this.minutes = seconds / 60;
        this.hours = minutes / 60;
        return this;
    }

    public Time minutes(double minutes) {
        this.seconds = minutes * 60;
        this.milliSeconds = seconds * 1000;
        this.minutes = seconds / 60;
        this.hours = minutes / 60;
        return this;
    }

    public Time hours(Double hours) {
        this.minutes = hours * 60;
        this.seconds = minutes * 60;
        this.milliSeconds = seconds * 1000;
        this.minutes = seconds / 60;
        this.hours = minutes / 60;
        return this;
    }

    public Time days(Double days) {
        this.hours = days * 24;
        this.minutes = hours * 60;
        this.seconds = minutes * 60;
        this.milliSeconds = seconds * 1000;
        this.minutes = seconds / 60;
        this.hours = minutes / 60;
        return this;
    }

    public Time ms(Double ms) {
        return milliSeconds(ms);
    }

    public Time sec(Double s) {
        return seconds(s);
    }

    public Time min(Double m) {
        return minutes(m);
    }

    public Time hour(Double h) {
        return hours(h);
    }

    public Double getMilliSeconds() {
        return milliSeconds;
    }

    public Double getSeconds() {
        return seconds;
    }

    public Double getMinutes() {
        return minutes;
    }

    public Double getHours() {
        return hours;
    }

    public Double getDays() {
        return days;
    }


    public static String time() {
        return new SimpleDateFormat("HH:mm:ss")
                .format(new Date());
    }

    public static String date() {
        return new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date());
    }

    public static String dateTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                .format(new Date());
    }
}
