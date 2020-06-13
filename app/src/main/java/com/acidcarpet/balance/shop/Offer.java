package com.acidcarpet.balance.shop;

import android.content.Context;

import com.acidcarpet.balance.R;

import java.util.ArrayList;
import java.util.List;

public class Offer {
    private static List<Offer> offers;
    public static List<Offer> getOffers(Context context){
        if(offers==null) offers = set_offers(context);
        return offers;
    }
    private static List<Offer> set_offers(Context context){
        List<Offer> offers = new ArrayList<>();
        offers.add(new Offer("+2м (тест)", 3, 21600000l));
        offers.add(new Offer(context.getResources().getString(R.string.shop_offer_hours_6), 3, 21600000l));
        offers.add(new Offer(context.getResources().getString(R.string.shop_offer_hours_12), 4, 43200000l));
        offers.add(new Offer(context.getResources().getString(R.string.shop_offer_day_1), 6, 86400000l));
        offers.add(new Offer(context.getResources().getString(R.string.shop_offer_day_7), 31, 604800000l));
        offers.add(new Offer(context.getResources().getString(R.string.shop_offer_day_30), 135, 2592000000l));

        return offers;
    }

    private String text;
    private int diamonds_coast;
    private long ms_add;

    public Offer(String text, int diamonds_coast, long ms_add){
        this.text = text;
        this.diamonds_coast = diamonds_coast;
        this.ms_add = ms_add;
    }

    public String getText() {
        return text;
    }
    public int getDiamonds_coast() {
        return diamonds_coast;
    }
    public long getMs_add() {
        return ms_add;
    }
}
