package com.acidcarpet.balance.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity
public class Record {

    @PrimaryKey
    public long id;

    public String date;

    public boolean good;

    public String day_date(){
        String out;

        Date from_date;
        Date to_date;

        try {
            SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            from_date = date_format.parse(date);
            SimpleDateFormat format1 = new SimpleDateFormat("HH:mm:ss");

            out =  format1.format(from_date);
        } catch (ParseException e) {
            out="---";
            e.printStackTrace();
        }

        return out;
    }

}
