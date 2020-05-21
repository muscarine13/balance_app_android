package com.acidcarpet.balance.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.acidcarpet.balance.MainActivity;

public class DBContainer {
    private static DBContainer instance;
    public static DBContainer getInstance(Context context){
        if (instance ==null) instance = new DBContainer(context);
        return instance;
    }

    private BalanceDatabase db;


    private DBContainer(Context context){
        db =  Room.databaseBuilder(context, BalanceDatabase.class, "maindb").build();
    }
}
