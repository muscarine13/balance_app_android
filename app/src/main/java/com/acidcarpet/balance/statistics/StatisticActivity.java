package com.acidcarpet.balance.statistics;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.acidcarpet.balance.main.MainActivity;
import com.acidcarpet.balance.R;
import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.data.RecordPack;

import java.util.Collections;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {
    private static Screen screen = Screen.DAY;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter mAdapter;
    private ImageButton cancel_button;
    private Button today_button;
    private Button days_button;
    private Button months_button;

    public void setAdapter(){
        switch (screen){
            case DAY:
                mAdapter = generate_day_adapter();
                break;
            case DAYS:
                mAdapter = generate_days_adapter();
                break;
            case MONTHS:
                mAdapter = generate_months_adapter();
                break;
            default:
        }
        recyclerView.setAdapter(mAdapter);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        recyclerView = (RecyclerView) findViewById(R.id.statistic_recycler_view);

        cancel_button = (ImageButton)  findViewById(R.id.statistics_cancel_button);
        today_button = (Button)  findViewById(R.id.statistics_today_button);
        days_button = (Button)  findViewById(R.id.statistics_days_button);
        months_button = (Button)  findViewById(R.id.statistics_months_button);


        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel_click();
            }
        });
        today_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                today_click();
            }
        });
        days_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                days_click();
            }
        });
        months_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                months_click();
            }
        });


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        setAdapter();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_statistic);
        recyclerView = (RecyclerView) findViewById(R.id.statistic_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        // specify an adapter (see also next example)
        setAdapter();

        recyclerView.setAdapter(mAdapter);
    }

    public RecyclerView.Adapter generate_day_adapter(){
        List<Record> pre_temp_day;
        pre_temp_day = DBContainer.getInstance(StatisticActivity.this).day();
        Record[] temp_day;
        temp_day = new Record[pre_temp_day.size()];

        for (int i = 0; i<pre_temp_day.size();i++){
            temp_day[i] = pre_temp_day.get(i);
            System.out.println(temp_day[i]);;
        }

        return new DayAdapter(temp_day);
    }
    public RecyclerView.Adapter generate_days_adapter(){
        List<RecordPack> pre_temp_days;
        pre_temp_days = DBContainer.getInstance(StatisticActivity.this).days();

        RecordPack[] temp_days;
        temp_days = new RecordPack[pre_temp_days.size()];

        Collections.reverse(pre_temp_days);

        for (int i = 0; i<pre_temp_days.size();i++){
            temp_days[i] = pre_temp_days.get(i);
        }

        return new DaysAdapter(temp_days);
    }
    public RecyclerView.Adapter generate_months_adapter(){
        List<RecordPack> pre_temp_months;
        pre_temp_months = DBContainer.getInstance(StatisticActivity.this).months();

        RecordPack[] temp_months;
        temp_months = new RecordPack[pre_temp_months.size()];

        for (int i = 0; i<pre_temp_months.size();i++){
            temp_months[i] = pre_temp_months.get(i);
        }

        return new MonthsAdapter(temp_months);
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

    private void today_click(){
        screen = Screen.DAY;
        startActivity(new Intent(this, StatisticActivity.class));
    }
    private void days_click(){
        screen = Screen.DAYS;
        startActivity(new Intent(this, StatisticActivity.class));
    }
    private void months_click(){
        screen = Screen.MONTHS;
        startActivity(new Intent(this, StatisticActivity.class));
    }
    private void cancel_click(){
        startActivity(new Intent(this, MainActivity.class));
    }
}