package com.acidcarpet.balance;

import java.util.Random;

public class Wrench {

    public static int random_int(int min, int max){
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }

    public static double random_double(double min, double max){
        Random r = new Random();
        return  min + ((max - min) * r.nextDouble());
    }

    public static boolean try_percent(double percent){
        if(random_double(0.0, 100.0)<=percent)return true;
        else return false;
    }

    public static boolean try_double(double double_percent){
        if(double_percent<=new Random().nextDouble())return true;
        else return false;
    }
}
