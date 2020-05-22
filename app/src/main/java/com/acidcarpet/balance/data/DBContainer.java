package com.acidcarpet.balance.data;

import android.content.Context;

import androidx.room.Room;

public class DBContainer {
    private static DBContainer instance;
    public static DBContainer getInstance(Context context){
        if (instance ==null) instance = new DBContainer(context);
        return instance;
    }

    private BalanceDatabase db;


    private DBContainer(Context context){
        db =  Room.databaseBuilder(context, BalanceDatabase.class, "maindb").allowMainThreadQueries().build();
    }

    public BalanceDatabase getDB(){
        return db;
    }

    public double good_percent(){
        int good = 0;
        int bad = 0;

        for(Record rec : db.mRecordDao().getAll()){
            if(rec.good) good++;
            else bad++;
        }
        double good_percent = (double)good/((double)bad+(double)good);
        return good_percent;
    }


}
