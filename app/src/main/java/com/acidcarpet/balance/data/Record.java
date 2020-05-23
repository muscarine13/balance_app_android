package com.acidcarpet.balance.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
public class Record {

    @PrimaryKey
    public long id;

    public long date;

    public boolean good;

    public String day_date(){
        String out;

        Date from_date;
        Date to_date;

        from_date = new Date(date);

        SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");

        out =  format1.format(from_date);

        return out;
    }

}
