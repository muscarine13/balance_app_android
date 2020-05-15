package com.acidcarpet.balance;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    NumberFormat formatter = new DecimalFormat("#0.00");


    private Button good_button;
    private Button bad_button;
    private TextView good_percent_label;
    private TextView bad_percent_label;
    private TextView motivation_label;
    private ProgressBar balance_bar;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info_button:

                TutorialActivity.activate();
                startActivity(new Intent(this, TutorialActivity.class));

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        good_button = (Button) findViewById(R.id.good_button);
        bad_button = (Button) findViewById(R.id.bad_button);
        good_percent_label = (TextView) findViewById(R.id.good_label);
        bad_percent_label = (TextView) findViewById(R.id.bad_label);
        motivation_label = (TextView) findViewById(R.id.motivation_label);
        balance_bar = (ProgressBar) findViewById(R.id.balance_bar);



        good_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                good_tap();
            }
        });
        bad_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bad_tap();
            }
        });

        refresh();
    }





    private void good_tap(){

        Storage.get().add_good_tap();
        refresh();
        Toast.makeText(MainActivity.this,
                R.string.good_toast,
                Toast.LENGTH_SHORT).show();
    }

    private void bad_tap(){

        Storage.get().add_bad_tap();
        refresh();
        Toast.makeText(MainActivity.this,
                R.string.bad_toast,
                Toast.LENGTH_SHORT).show();
    }



    private void refresh(){

        good_percent_label.setText(formatter.format(Storage.get().get_good_percent()*100)+"%");
        bad_percent_label.setText(formatter.format(Storage.get().get_bad_percent()*100)+"%");

        motivation_label.setText("Курочка по зернышку клюёт, а двор весь в помете. \nРусский народ, неизвестный год.");

        balance_bar.setProgress((int)(Storage.get().get_bad_percent()*1000)-1);

    }
}