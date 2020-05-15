package com.acidcarpet.balance;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;


public class Storage {
    private static final String LOG_TAG = "STORAGE";
    private static final String FILENAME = "storage";

    private static Storage instance;
    public static Storage get(){
        if (instance==null) instance = new Storage();
        return instance;
    }
    public static void set(Storage storage){
        instance = storage;
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
    
   public String serial(){
        String out;
        out=
        good_taps+","+
                bad_taps;

        return out;
   }
   public static boolean de_serial(String file){
        try{
            String[] array = file.split(",");
            int good_taps = Integer.parseInt(array[0]);
            int bad_taps = Integer.parseInt(array[1]);
            if(instance==null) instance = new Storage();
            instance.good_taps = good_taps;
            instance.bad_taps = bad_taps;
            return true;
        }catch (Exception e){
            return false;
        }
   }



}
