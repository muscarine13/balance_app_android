package com.acidcarpet.balance.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecordDao {

    @Query("SELECT * FROM record")
    List<Record> getAll();

    @Query("SELECT * FROM record WHERE date BETWEEN :from and :to")
    List<Record> getFromTo(long from, long to);

    @Query("SELECT * FROM record WHERE id = :id")
    Record getById(long id);


    @Query("SELECT max(date) FROM record")
    long getMax();

    @Query("SELECT min(date) FROM record")
    long getMin();

    @Insert
    void insert(Record record);

    @Update
    void update(Record record);

    @Delete
    void delete(Record record);
}
