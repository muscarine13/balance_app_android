package com.acidcarpet.balance.data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecordPack {

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

    public String days_date(){
        String out;

//        Date from_date;
//        Date to_date;
//
//        from_date = new Date(from);
//        to_date = new Date(to);
//
//        SimpleDateFormat format1 = new SimpleDateFormat("dd.MM");
//
//        out =  format1.format(from_date);
        out = records.get(0).date;

        return out;
    }
    public String months_date(){
        String out;

        Date from_date;
        Date to_date;

        from_date = new Date(from);
        to_date = new Date(to);

        SimpleDateFormat format1 = new SimpleDateFormat("MM:yyyy");

        out =  format1.format(from_date);

        return out;
    }
}
