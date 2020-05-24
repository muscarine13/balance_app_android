package com.acidcarpet.balance.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.acidcarpet.balance.MainActivity;

import java.text.SimpleDateFormat;
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

    public List<Record> day(){
        List<Record> out;

        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");

        String query =
                format1.format(new Date())+"%";

        out = getDB().mRecordDao().getDay(query);

        return out;
    }
    public List<RecordPack> days() {
        Log.d("DBC", "Начали days");
        List<RecordPack> out = new ArrayList<>();

        RecordDao dao = db.mRecordDao();

        //List<Record> days = dao.getDays();

//        for (List<Record> day : days){
//
//            if(day!=null&&!day.isEmpty()){
//                out.add(new RecordPack(day.get(0).date, day.get(day.size()-1).date, day));
//            }
//
//        }

//        long min = dao.getMin();
//        long max = dao.getMax();
//
//        long from = getStartOfADay(new Date(min)).getTime();
//        long to = getEndOfADay(new Date(min)).getTime();
//        Log.d("DBC", "FROM:TO - "+from+":"+to);
//
//
//        do {
//
//            List<Record> records = dao.getFromTo(from, to);
//            if(!records.isEmpty()){
//                out.add(new RecordPack(from, to, records));
//            }
//
//            System.out.println("PACK:\n"+from+to+records.size());
//
//            from += 86400000;
//            to += 86400000;
//        } while (to < max);

        return out;
    }
    public List<RecordPack> months() {
        Log.d("DBC", "Начали months");
        List<RecordPack> out = new ArrayList<>();

        RecordDao dao = db.mRecordDao();
//
//        long min = dao.getMin();
//        long max = dao.getMax();
//
//        long from = getStartOfAMonth(new Date(min)).getTime();
//        long to = getEndOfAMonth(new Date(min)).getTime();
//
//
//
//        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        Log.d("DBC", "FROM:TO - "+format1.format(new Date(from))+":"+format1.format(new Date(to)));
//
//
//        do {
//
//            List<Record> records = dao.getFromTo(from, to);
//            if(!records.isEmpty()){
//                out.add(new RecordPack(from, to, records));
//            }
//
//            System.out.println("PACK:\n"+from+to+records.size());
//
//            from = getStartOfAMonth(new Date(to+10000)).getTime();
//            to = getEndOfAMonth(new Date(from)).getTime();
//
//        } while (to < max);

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
    static public Date getEndOfAMonth(Date day) {
        Date start = getStartOfAMonth(day);

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTimeInMillis(start.getTime());

        calendar.add(Calendar.MONTH , +1);

        long firstDayOfMonthTimestamp = calendar.getTimeInMillis();
        return new Date(firstDayOfMonthTimestamp);

    }

}
