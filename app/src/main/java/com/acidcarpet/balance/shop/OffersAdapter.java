package com.acidcarpet.balance.shop;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.RecyclerView;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.data.BalanceDatabase;
import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.data.RecordDao;
import com.acidcarpet.balance.statistics.StatisticActivity;

import java.util.Date;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.MyViewHolder> {

    private Offer[] dataset;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView text_textView;
        public TextView diamond_coast_textView;
        public ImageButton offer_buy_button;

        public MyViewHolder(View view) {
            super(view);

            this.text_textView = (TextView) view.findViewById(R.id.offer_text_textView);
            this.diamond_coast_textView = (TextView) view.findViewById(R.id.offer_diamonds_coast_textView);
            this.offer_buy_button = (ImageButton) view.findViewById(R.id.offer_buy_button);
        }
    }


    public OffersAdapter(Offer[] dataset) {
        this.dataset = dataset;
    }


    @Override
    public OffersAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_offer, parent, false);
       ///
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.text_textView.setText(dataset[position].getText());
        holder.diamond_coast_textView.setText(dataset[position].getDiamonds_coast()+"");

        if(dataset[position].getDiamonds_coast()<=1111){
            holder.offer_buy_button.setEnabled(true);
            holder.offer_buy_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new BuyOffer(v, dataset[position]).run();
                }
            });

        }else{
            holder.offer_buy_button.setEnabled(false);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return dataset.length;
    }



    public class BuyOffer implements Runnable {
        Offer offer;
        View view;

        public BuyOffer(View view, Offer offer){
            this.offer = offer;
            this.view = view;
        }

        @Override
        public void run() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(view.getContext());
            int diamonds = preferences.getInt("diamonds",0);
            long ad_free_time = preferences.getLong("ad_free_date", 0);

            long now = new Date().getTime();

            if(diamonds>=offer.getDiamonds_coast()){
                preferences.edit().putInt("diamonds", diamonds-offer.getDiamonds_coast()).apply();

                if(ad_free_time<=now){
                    preferences.edit().putLong("ad_free_date", now+offer.getMs_add()).apply();
                }else{
                    preferences.edit().putLong("ad_free_date", ad_free_time+offer.getMs_add()).apply();
                }
            }


            notifyDataSetChanged();
            ShopActivity activity =(ShopActivity) view.getContext();
            activity.refresh();
        }
    }
}
