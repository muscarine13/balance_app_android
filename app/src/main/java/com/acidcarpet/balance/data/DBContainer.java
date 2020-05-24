package com.acidcarpet.balance.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.acidcarpet.balance.MainActivity;

import java.text.ParseException;
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

    public static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        String min = dao.getMin();
        String max = dao.getMax();


        try {
            SimpleDateFormat day_format = new SimpleDateFormat("yyyy-MM-dd");
            Date start_time = date_format.parse(min);
            Date end_time = date_format.parse(max);

            String formatted_current = day_format.format(start_time);
            String formatted_max = day_format.format(end_time);

            if (formatted_current.equals(formatted_max)) {
                System.err.println("День всего один");
                RecordPack pack;

                List<Record> records = dao.getDay(formatted_current+ "%");
                //System.err.println("RECORDS"+records.isEmpty());
                if (records != null&&!records.isEmpty()) {
                    System.err.println("Рекорд пак не пуст");
                    pack = new RecordPack(records.get(0).date, records.get(records.size() - 1).date, records);
                    out.add(pack);
                }

            } else {
                System.err.println("Дней несколько");
                do {
                    System.err.println("Начало итерации");
                    RecordPack pack;

                    Calendar current = Calendar.getInstance();
                    current.setTime(day_format.parse(formatted_current));

                    List<Record> records = dao.getDay(day_format.format(current.getTime()) + "%");
                    if (records != null&&!records.isEmpty()) {
                        System.err.println("День существует и не пуст");
                        pack = new RecordPack(records.get(0).date, records.get(records.size() - 1).date, records);
                        out.add(pack);
                    }

                    current.add(Calendar.DATE, 1);
                    formatted_current = day_format.format(current.getTime());

                } while (!formatted_current.equals(formatted_max));

                RecordPack pack;
                List<Record> records = dao.getDay(formatted_max + "%");
                if (records != null&&!records.isEmpty()) {
                    System.err.println("День существует и не пуст");
                    pack = new RecordPack(records.get(0).date, records.get(records.size() - 1).date, records);
                    out.add(pack);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return out;
    }
        public List<RecordPack> months() {
        Log.d("DBC", "Начали months");
        List<RecordPack> out = new ArrayList<>();

        //RecordDao dao = db.mRecordDao();
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


}
