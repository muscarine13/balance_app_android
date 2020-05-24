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

    @Query("SELECT * FROM record ORDER BY date DESC")
    List<Record> getAll();

    @Query("SELECT * FROM record WHERE date BETWEEN :from and :to ORDER BY date DESC")
    List<Record> getFromTo(long from, long to);

    @Query("SELECT * FROM record WHERE id = :id")
    Record getById(long id);

    @Query("SELECT COUNT(good=1) FROM record;")
    int getGoodSum();

    @Query("SELECT COUNT(good=0) FROM record;")
    int getBadSum();

    @Query("SELECT *\n" +
            "FROM record\n" +
            "WHERE date LIKE :date_plus_percent " +
            "ORDER BY date DESC"
            )
    List<Record> getDay(String date_plus_percent);


    @Query("SELECT max(date) FROM record")
    String getMax();

    @Query("SELECT min(date) FROM record")
    String getMin();

    @Insert
    void insert(Record record);

    @Update
    void update(Record record);

    @Delete
    void delete(Record record);
}
