package com.acidcarpet.balance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.data.RecordPack;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {
    NumberFormat formatter = new DecimalFormat("#0.00");

    private RecordPack[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView date_text_view;

        public TextView good_text_view;
        public TextView bad_text_view;

        public Button delete_button;

        public MyViewHolder(View view) {
            super(view);

            this.date_text_view = (TextView) view.findViewById(R.id.item_date_text_view);

            this.date_text_view = (TextView) view.findViewById(R.id.item_date_text_view);
            this.good_text_view = (TextView) view.findViewById(R.id.item_good_text_view);
            this.bad_text_view = (TextView) view.findViewById(R.id.item_bad_text_view);
            this.delete_button = (Button) view.findViewById(R.id.item_delete_button);
        }



    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DayAdapter(RecordPack[] myDataset) {
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
        holder.bad_text_view.setText(formatter.format(mDataset[position].bad_sum()*100)+"%");
        holder.good_text_view.setText(formatter.format(mDataset[position].good_sum()*100)+"%");

        holder.delete_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                /// delete action
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
