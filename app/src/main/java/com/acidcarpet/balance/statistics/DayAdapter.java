package com.acidcarpet.balance.statistics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.data.BalanceDatabase;
import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.data.RecordDao;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {
    NumberFormat formatter = new DecimalFormat("#0.00");

    private Record[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView date_text_view;

        public TextView good_text_view;

        public ImageButton day_delete_button;


        public MyViewHolder(View view) {
            super(view);

            this.date_text_view = (TextView) view.findViewById(R.id.day_date_text_view);
            this.good_text_view = (TextView) view.findViewById(R.id.day_good_text_view);
            this.day_delete_button = (ImageButton) view.findViewById(R.id.day_delete_button);

        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DayAdapter(Record[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day, parent, false);
       ///
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.date_text_view.setText(mDataset[position].day_date());

        if(mDataset[position].good){
            holder.good_text_view.setText(R.string.statistics_item_day_good);
            holder.good_text_view.setTextColor(Color.parseColor("#43A047"));
            //
        }else{
            holder.good_text_view.setText(R.string.statistics_item_day_bad);
            holder.good_text_view.setTextColor(Color.parseColor("#E53935"));
        }

        holder.day_delete_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

              Activity activity =   (Activity)v.getContext();

                      activity.runOnUiThread(new DeleteThread(v, mDataset[position]));


            }
        });

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
