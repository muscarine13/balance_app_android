package com.acidcarpet.balance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.data.RecordPack;

public class StatisticActivity extends AppCompatActivity {

    private static Screen screen = Screen.DAY;
    private static boolean active = false;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

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
        recyclerView = (RecyclerView) findViewById(R.id.statistic_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        RecordPack[] temp = new RecordPack[DBContainer.getInstance(StatisticActivity.this).days().size()];

        for (int i = 0; i<DBContainer.getInstance(StatisticActivity.this).days().size();i++){
            temp[i] = DBContainer.getInstance(StatisticActivity.this).days().get(i);
        }

        mAdapter = new DayAdapter(temp);
        recyclerView.setAdapter(mAdapter);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.statistic_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.statistics_day_button:

                //startActivity(new Intent(this, Statistics.class));
                return true;

            case R.id.statistics_week_button:

                ///startActivity(new Intent(this, StatisticActivity.class));
                return true;

            case R.id.statistics_month_button:

                ///

                return true;

            case R.id.statistics_close_button:

                startActivity(new Intent(this, MainActivity.class));
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
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