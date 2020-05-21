package com.acidcarpet.balance.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Record {

    @PrimaryKey
    long id;

    Date date;

    boolean good;

}
