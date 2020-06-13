package com.acidcarpet.balance.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.main.MainActivity;
import com.acidcarpet.balance.statistics.DayAdapter;
import com.acidcarpet.balance.statistics.StatisticActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements RewardedVideoAdListener {

    private RewardedVideoAd mRewardedVideoAd;

    //private String ad_id = "ca-app-pub-2464895162956927/7011701805"; //main
    private String ad_id = "ca-app-pub-3940256099942544/5224354917"; //test

    public static final String TAG = "SHOP_ACTIVITY";
    public static final SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yyy");
    public static final SimpleDateFormat time_format = new SimpleDateFormat("HH:mm:ss");

    private SharedPreferences preferences;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private ImageButton cancel_button;
    private TextView diamonds_textView;

    private TextView date_textView;

    private Button watch_ad_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);

        recyclerView = findViewById(R.id.shop_offers_recyclerView);

        cancel_button = findViewById(R.id.shop_cancel_button);
        diamonds_textView = findViewById(R.id.shop_diamond_textView);

        date_textView = findViewById(R.id.shop_date_label);

        watch_ad_button = findViewById(R.id.shop_watch_ad_button);

        watch_ad_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watch_click();
            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_click();
            }
        });



        MobileAds.initialize(this, ad_id);
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

        loadRewardedVideoAd();

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = generate_offers_adapter();
        recyclerView.setAdapter(adapter);

        refresh();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(adapter);
        refresh();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_shop);
        recyclerView = (RecyclerView) findViewById(R.id.shop_offers_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = generate_offers_adapter();

        recyclerView.setAdapter(adapter);
        refresh();
    }


    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        int diamonds = preferences.getInt("diamonds", 0);
        preferences.edit().putInt("diamonds", diamonds+1).apply();
        refresh();
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd(ad_id,
                new AdRequest.Builder().build());
    }

    public void watch_click(){

        if (mRewardedVideoAd.isLoaded()) {
            mRewardedVideoAd.show();
        }
//    int diamonds = preferences.getInt("diamonds", 0);
//    preferences.edit().putInt("diamonds", diamonds+1).apply();

    refresh();

    }
    public void cancel_click(){
        startActivity(new Intent(this, MainActivity.class));
    }

    public RecyclerView.Adapter generate_offers_adapter(){
        List<Offer> pre_temp_offers = Offer.getOffers(this);

        Offer[] temp_offers;
        temp_offers = new Offer[pre_temp_offers.size()];

        for (int i = 0; i<pre_temp_offers.size();i++){
            temp_offers[i] = pre_temp_offers.get(i);
        }

        return new OffersAdapter(temp_offers);
    }

    public void refresh(){
        Log.d(TAG, "Зашли в рефреш");
        long now = new Date().getTime();
        long expired = preferences.getLong("ad_free_date", 0);
        Log.d(TAG, "Сейчас:"+now+"\n"+"Истекает:"+expired);

        int diamonds = preferences.getInt("diamonds", 0);
        Log.d(TAG, "Алмазов:"+diamonds);

        try{
            diamonds_textView.setText(diamonds+"");
        }catch (Exception e){
            Log.d(TAG, "Ошибка:"+e.getMessage());
        }


        if(expired<=now){
            date_textView.setText("ПОКАЗ РЕКЛАМЫ");

        }else{
            String date_string =
                    date_format.format(new Date(expired))
                    +"\n"
                    +time_format.format(new Date(expired));

            date_textView.setText(date_string);
        }

    }
}