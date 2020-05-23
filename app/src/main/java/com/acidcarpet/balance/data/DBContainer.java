package com.acidcarpet.balance.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.acidcarpet.balance.MainActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class DBContainer {
    private static DBContainer instance;

    public static DBContainer getInstance(Context context) {
        if (instance == null) instance = new DBContainer(context);
        return instance;
    }

    private BalanceDatabase db;


    private DBContainer(Context context) {
        db = Room.databaseBuilder(context, BalanceDatabase.class, "maindb").allowMainThreadQueries().build();
    }

    public BalanceDatabase getDB() {
        return db;
    }

    public double good_percent() {
        int good = 0;
        int bad = 0;

        for (Record rec : db.mRecordDao().getAll()) {
            if (rec.good) good++;
            else bad++;
        }
        double good_percent = (double) good / ((double) bad + (double) good);
        return good_percent;
    }

    public List<RecordPack> days() {
        List<RecordPack> out = new ArrayList<>();

        RecordDao dao = db.mRecordDao();

        long min = 0;
        long max = 0;

        for (Record rec : dao.getAll()) {
            if (min == 0) min = rec.date;
            if (max == 0) max = rec.date;

            if (rec.date > max) max = rec.date;
            if (rec.date < min) min = rec.date;
        }

        long from = getStartOfADay(new Date(min)).getTime();
        long to = getEndOfADay(new Date(min)).getTime();

        do {
            List<Record> records = dao.getFromTo(from, to);
            out.add(new RecordPack(from, to, records));

            from += 86400000;
            to += 86400000;
        } while (to < max);


        return out;
    }
    public List<RecordPack> weeks() {

        List<RecordPack> out = new ArrayList<>();

        RecordDao dao = db.mRecordDao();

        long min = 0;
        long max = 0;

        for (Record rec : dao.getAll()) {
            if (min == 0) min = rec.date;
            if (max == 0) max = rec.date;

            if (rec.date > max) max = rec.date;
            if (rec.date < min) min = rec.date;
        }

        long from = getStartOfAWeek(new Date(min)).getTime();
        long to = from+604800000;

        do {
            List<Record> records = dao.getFromTo(from, to);
            out.add(new RecordPack(from, to, records));

            from += 604800000;
            to += 604800000;
        } while (to < max);


        return out;


    }
    public List<RecordPack> month() {
        List<RecordPack> out = new ArrayList<>();

        RecordDao dao = db.mRecordDao();

        long min = 0;
        long max = 0;

        for (Record rec : dao.getAll()) {
            if (min == 0) min = rec.date;
            if (max == 0) max = rec.date;

            if (rec.date > max) max = rec.date;
            if (rec.date < min) min = rec.date;
        }

        long from = getStartOfAMonth(new Date(min)).getTime();
        long to = from+86400000;

        do {
            List<Record> records = dao.getFromTo(from, to);
            out.add(new RecordPack(from, to, records));

            from += 86400000;
            to += 86400000;
        } while (to < max);


        return out;
    }


    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    static public Date getStartOfADay(Date day) {
        final long oneDayInMillis = 24 * 60 * 60 * 1000;
        return new Date(day.getTime() / oneDayInMillis * oneDayInMillis);
    }
    static public Date getEndOfADay(Date day) {
        final long oneDayInMillis = 24 * 60 * 60 * 1000;
        return new Date((day.getTime() / oneDayInMillis + 1) * oneDayInMillis - 1);
    }

    static public Date getStartOfAWeek(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(day.getTime());
        while (calendar.get(Calendar.DAY_OF_WEEK) > calendar.getFirstDayOfWeek()) {
            calendar.add(Calendar.DATE, -1);
            // Substract 1 day until first day of week. }
        }
        long firstDayOfWeekTimestamp = calendar.getTimeInMillis();
        return new Date(firstDayOfWeekTimestamp);

    }
    static public Date getStartOfAMonth(Date day) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(day.getTime());
        while (calendar.get(Calendar.DATE) > 1) {
            calendar.add(Calendar.DATE, -1);
            // Substract 1 day until first day of month.
        }
        long firstDayOfMonthTimestamp = calendar.getTimeInMillis();
        return new Date(firstDayOfMonthTimestamp);

    }

}
