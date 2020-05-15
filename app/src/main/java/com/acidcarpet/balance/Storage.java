package com.acidcarpet.balance;

public class Storage {
    private static Storage instance;
    public static Storage get(){
        if (instance==null) instance = new Storage();
        return instance;
    }

    private Storage(){
        good_taps= 1;
        bad_taps = 1;
    }
    private int good_taps;
    private int bad_taps;

    public void add_good_tap(){
        good_taps++;
    }
    public void add_bad_tap(){
        bad_taps++;
    }

    public double get_sum(){
        return good_taps+bad_taps;
    }
    public int get_good_taps() {
        return good_taps;
    }
    public int get_bad_taps() {
        return bad_taps;
    }

    public double get_good_percent(){
        return (double)good_taps/((double)good_taps+(double)bad_taps);
    }
    public double get_bad_percent(){
        return (double)bad_taps/((double)bad_taps+(double)good_taps);
    }

}
