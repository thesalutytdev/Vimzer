package org.thesalutyt.vimzer.back;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HistoryPage {
    private String page;
    private String title;
    private Date date;
    private String dateString;

    public HistoryPage(String page, String title, Date date) {
        this.page = page;
        this.title = title;
        this.date = date;
        this.dateString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public HistoryPage(String page, String title) {
        this(page, title, new Date());
    }

    public static HistoryPage fromString(String string) {
        String[] split = string.split(";");

        try {
            return new HistoryPage(split[0], split[1], new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(split[2]));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPage() {
        return page;
    }

    public String getTitle() {
        return title;
    }

    public String getDateString() {
        return dateString;
    }

    public Date getDate() {
        return date;
    }

    public String beautify() {
        return String.format("%s - %s", page, dateString);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s", page, title, dateString);
    }
}
