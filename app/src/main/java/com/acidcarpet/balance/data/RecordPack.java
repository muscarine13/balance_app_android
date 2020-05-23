package com.acidcarpet.balance.data;

import java.util.Date;
import java.util.List;

public class RecordPack {

    private long from;
    private long to;

    private List<Record> records;

    public RecordPack(long from, long to, List<Record> records){
        this.from = from;
        this.to = to;
        this.records = records;
    }

    public long getFrom() {
        return from;
    }
    public long getTo() {
        return to;
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
                good_sum()
                        /
                        (bad_sum()+good_sum());
    }

    public String day_date(){
        String out;

        Date from_date;
        Date to_date;

        from_date = new Date(from);
        to_date = new Date(to);

        out=""+
                from_date.getDay()+1+"."+from_date.getMonth()+1+
                "\n"+from_date.getYear();


        return out;
    }
}
