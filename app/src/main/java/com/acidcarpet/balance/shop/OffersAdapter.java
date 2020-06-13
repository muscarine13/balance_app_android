package com.acidcarpet.balance.shop;

import android.app.Activity;
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

                }
            });

        }else{
            holder.offer_buy_button.setEnabled(false);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }



    public class DeleteThread implements Runnable {
        Record mRecord;
        View mView;

        public DeleteThread(View view, Record aRecord){
            mRecord = aRecord;
            mView = view;
        }

        @Override
        public void run() {
            BalanceDatabase db = DBContainer.getInstance(mView.getContext()).getDB();
            RecordDao recordDao = db.mRecordDao();
            recordDao.delete(mRecord);
            //System.err.println("Перед изменением");
            notifyDataSetChanged();
            StatisticActivity activity =(StatisticActivity) mView.getContext();
            activity.setAdapter();
            //System.err.println("После изменения");
        }
    }
}
