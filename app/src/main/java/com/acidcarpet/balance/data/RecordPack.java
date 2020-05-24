package com.acidcarpet.balance.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecordPack {
    public static final SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat formatted_days = new SimpleDateFormat("dd.MM");
    public static final SimpleDateFormat formatted_months = new SimpleDateFormat("MM yyyy");

    private String from;
    private String to;

    private List<Record> records;

    public RecordPack(String from, String to, List<Record> records){
        this.from = from;
        this.to = to;
        this.records = records;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public int good_sum(){
        int out = 0;

        for(Record rec : records){
            if(rec.good) out++;
        }
        return out;
    }
    public int bad_sum(){
        int out = 0;

        for(Record rec : records){
            if(!rec.good) out++;
        }
        return out;
    }
    public double good_percent(){
        return
                (double)good_sum()
                        /
                        ((double)bad_sum()+(double)good_sum());
    }

    public String days_date() {
        String out = "---";

        try {
            Date date = date_format.parse(from);
            out = formatted_days.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return out;
    }
    public String months_date(){
        String out = "---";
        System.err.println("Дата месяца начали");

        try {
            Date date = date_format.parse(from);
            out = formatted_months.format(date);
            System.err.println("Успешно закончили трай");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        System.err.println("Возвращаем:"+out);
        return out;
    }
}
