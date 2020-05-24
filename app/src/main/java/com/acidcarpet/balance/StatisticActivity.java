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
import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.data.RecordPack;

import java.util.Collections;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {

    private static Screen screen = Screen.DAY;
    private static boolean active = false;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;

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

        List<RecordPack> pre_temp;



        switch (screen){
            case DAY:
                System.err.println("ЗАШЛИ В ДЕНЬ");
                List<Record> pre_temp_day;
                pre_temp_day = DBContainer.getInstance(StatisticActivity.this).day();
                System.err.println("ИЗ БАЗЫ:"+DBContainer.getInstance(StatisticActivity.this).day().isEmpty());
                Record[] temp_day;
                temp_day = new Record[pre_temp_day.size()];

                for (int i = 0; i<pre_temp_day.size();i++){
                    temp_day[i] = pre_temp_day.get(i);
                    System.out.println(temp_day[i]);;
                }

                mAdapter = new DayAdapter(temp_day);
                break;
            case DAYS:
                List<RecordPack> pre_temp_days;
                pre_temp_days = DBContainer.getInstance(StatisticActivity.this).days();

                RecordPack[] temp_days;
                temp_days = new RecordPack[pre_temp_days.size()];

                Collections.reverse(pre_temp_days);

                for (int i = 0; i<pre_temp_days.size();i++){
                    temp_days[i] = pre_temp_days.get(i);
                }

                mAdapter = new DaysAdapter(temp_days);
                break;

            case MONTHS:
                List<RecordPack> pre_temp_months;
                pre_temp_months = DBContainer.getInstance(StatisticActivity.this).months();

                RecordPack[] temp_months;
                temp_months = new RecordPack[pre_temp_months.size()];

                Collections.reverse(pre_temp_months);

                for (int i = 0; i<pre_temp_months.size();i++){
                    temp_months[i] = pre_temp_months.get(i);
                }

                mAdapter = new MonthsAdapter(temp_months);
                break;
            default:
        }

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

                screen = Screen.DAY;
                startActivity(new Intent(this, StatisticActivity.class));

                return true;

            case R.id.statistics_week_button:

                screen = Screen.DAYS;
                startActivity(new Intent(this, StatisticActivity.class));

                return true;

            case R.id.statistics_month_button:

                screen = Screen.MONTHS;
                startActivity(new Intent(this, StatisticActivity.class));

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
        DAYS("DAYS"),
        MONTHS("MONTHS");

        String text;

        Screen(String text){
            this.text = text;
        }

        public String getText() {
            return text;
        }
    }
}