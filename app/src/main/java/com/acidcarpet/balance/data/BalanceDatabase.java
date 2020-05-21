package com.acidcarpet.balance.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Record.class}, version = 1)
public abstract class BalanceDatabase extends RoomDatabase {


    public abstract RecordDao mRecordDao();


}
