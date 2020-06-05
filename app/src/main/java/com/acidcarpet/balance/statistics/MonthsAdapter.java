package com.acidcarpet.balance.statistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.data.RecordPack;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MonthsAdapter extends RecyclerView.Adapter<MonthsAdapter.MyViewHolder> {
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

        public MyViewHolder(View view) {
            super(view);

            this.date_text_view = (TextView) view.findViewById(R.id.months_date_text_view);
            this.good_text_view = (TextView) view.findViewById(R.id.months_good_text_view);
            this.bad_text_view = (TextView) view.findViewById(R.id.months_bad_text_view);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MonthsAdapter(RecordPack[] myDataset) {
        mDataset = myDataset;
    }
    // Create new views (invoked by the layout manager)
    @Override
    public MonthsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_months, parent, false);
       ///
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.date_text_view.setText(mDataset[position].months_date() + "");

        holder.bad_text_view.setText(formatter.format((1-mDataset[position].good_percent())*100)+"");
        holder.good_text_view.setText(formatter.format(mDataset[position].good_percent()*100)+"");
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
