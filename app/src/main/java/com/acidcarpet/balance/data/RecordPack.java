package com.acidcarpet.balance.data;

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
}
