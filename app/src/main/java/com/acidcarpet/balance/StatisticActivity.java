package com.acidcarpet.balance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class StatisticActivity extends AppCompatActivity {

    private static Screen screen = Screen.DAY;
    private static boolean active = false;

    public static void activate(){
        active = true;
        screen = Screen.DAY;
    }
    public static void deactivate(){
        active = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
    }



    public enum Screen{
        DAY("DAY"),
        WEEK("WEEK"),
        MONTH("MONTH");

        String text;

        Screen(String text){
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}