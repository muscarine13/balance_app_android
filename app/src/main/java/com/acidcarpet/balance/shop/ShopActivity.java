package com.acidcarpet.balance.shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.main.MainActivity;
import com.acidcarpet.balance.statistics.DayAdapter;
import com.acidcarpet.balance.statistics.StatisticActivity;

import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private ImageButton cancel_button;
    private TextView diamonds_textView;

    private TextView date_textView;
    private TextView days_textView;
    private TextView hours_textView;
    private TextView minutes_textView;

    private Button watch_ad_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        recyclerView = findViewById(R.id.shop_offers_recyclerView);

        cancel_button = findViewById(R.id.shop_cancel_button);
        diamonds_textView = findViewById(R.id.shop_diamond_textView);

        date_textView = findViewById(R.id.shop_date_label);
        days_textView = findViewById(R.id.shop_days_label);
        hours_textView = findViewById(R.id.shop_hours_label);
        minutes_textView = findViewById(R.id.shop_minutes_label);

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

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = generate_offers_adapter();
    }

    public void watch_click(){

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
}