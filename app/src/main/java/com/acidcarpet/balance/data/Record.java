package com.acidcarpet.balance.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.UUID;

@Entity
public class Record {

    @PrimaryKey
    public long id;

    public long date;

    public boolean good;

}
