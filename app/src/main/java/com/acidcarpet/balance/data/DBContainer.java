package com.acidcarpet.balance.data;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        for (Record rec : day()) {
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

        out = getDB().mRecordDao().getDateQuery(query);

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

                List<Record> records = dao.getDateQuery(formatted_current+ "%");
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

                    List<Record> records = dao.getDateQuery(day_format.format(current.getTime()) + "%");
                    if (records != null&&!records.isEmpty()) {
                        System.err.println("День существует и не пуст");
                        pack = new RecordPack(records.get(0).date, records.get(records.size() - 1).date, records);
                        out.add(pack);
                    }

                    current.add(Calendar.DATE, 1);
                    formatted_current = day_format.format(current.getTime());

                } while (!formatted_current.equals(formatted_max));

                RecordPack pack;
                List<Record> records = dao.getDateQuery(formatted_max + "%");
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
            Log.d("DBC", "Начали days");
            List<RecordPack> out = new ArrayList<>();

            RecordDao dao = db.mRecordDao();

            String min = dao.getMin();
            String max = dao.getMax();


            try {
                SimpleDateFormat month_format = new SimpleDateFormat("yyyy-MM");
                Date start_time = date_format.parse(min);
                Date end_time = date_format.parse(max);

                String formatted_current = month_format.format(start_time);
                String formatted_max = month_format.format(end_time);

                if (formatted_current.equals(formatted_max)) {
                    System.err.println("Месяц всего один");
                    RecordPack pack;

                    List<Record> records = dao.getDateQuery(formatted_current+ "%");
                    //System.err.println("RECORDS"+records.isEmpty());
                    if (records != null&&!records.isEmpty()) {
                        System.err.println("Рекорд пак не пуст");
                        pack = new RecordPack(records.get(0).date, records.get(records.size() - 1).date, records);
                        out.add(pack);
                    }

                } else {
                    System.err.println("Месяцев несколько");
                    do {
                        System.err.println("Начало итерации");
                        RecordPack pack;

                        Calendar current = Calendar.getInstance();
                        current.setTime(month_format.parse(formatted_current));

                        List<Record> records = dao.getDateQuery(month_format.format(current.getTime()) + "%");
                        if (records != null&&!records.isEmpty()) {
                            System.err.println("Месяц существует и не пуст");
                            pack = new RecordPack(records.get(0).date, records.get(records.size() - 1).date, records);
                            out.add(pack);
                        }

                        current.add(Calendar.MONTH, 1);
                        formatted_current = month_format.format(current.getTime());

                    } while (!formatted_current.equals(formatted_max));

                    RecordPack pack;
                    List<Record> records = dao.getDateQuery(formatted_max + "%");
                    if (records != null&&!records.isEmpty()) {
                        System.err.println("Месяц существует и не пуст");
                        pack = new RecordPack(records.get(0).date, records.get(records.size() - 1).date, records);
                        out.add(pack);
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return out;
    }


}
