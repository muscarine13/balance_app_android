package com.acidcarpet.balance;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acidcarpet.balance.data.BalanceDatabase;
import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.data.RecordDao;

import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    InterstitialAd mInterstitialAd;

    ConsentForm form;
    private long motivator_last_changed;

    private static final String FILENAME = "storage";
    private static final String TAG = "MAIN_ACTIVITY";

    NumberFormat formatter = new DecimalFormat("#0.00");

    private Toast toast;
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
            case R.id.menu_tutorial_button:
                TutorialActivity.activate();
                startActivity(new Intent(this, TutorialActivity.class));
                return true;

            case R.id.menu_statistics_button:

                startActivity(new Intent(this, StatisticActivity.class));
                return true;

            case R.id.menu_settings_button:

                ///

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        motivator_last_changed = new Date().getTime();

        good_button = (Button) findViewById(R.id.good_button);
        bad_button = (Button) findViewById(R.id.bad_button);
        good_percent_label = (TextView) findViewById(R.id.good_label);
        bad_percent_label = (TextView) findViewById(R.id.bad_label);
        motivation_label = (TextView) findViewById(R.id.motivation_label);
        change_motivator();
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

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        //getConsentStatus();

        refresh();
    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void good_tap(){


        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                Log.d("TAG", "Заводим запись.");
                BalanceDatabase db = DBContainer.getInstance(MainActivity.this).getDB();

                RecordDao recordDao = db.mRecordDao();

                Record aRecord = new Record();

                aRecord.id = generateUniqueId();

                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                aRecord.date =  format1.format(new Date());

                //aRecord.date =  new Date().getTime();

                aRecord.good = true;

                recordDao.insert(aRecord);
                MainActivity.this.refresh();
                Log.d("TAG","Заводим запись: "+aRecord.id+" : "+aRecord.date+" : "+aRecord.good);


            }
        });


        refresh();
        toast.setText(R.string.good_toast);
        toast.show();
    }
    private void bad_tap(){

        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                Log.d("TAG", "Заводим запись.");
                BalanceDatabase db = DBContainer.getInstance(MainActivity.this).getDB();

                RecordDao recordDao = db.mRecordDao();

                Record aRecord = new Record();

                aRecord.id = generateUniqueId();

                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                aRecord.date =  format1.format(new Date());

                //aRecord.date =  new Date().getTime();

                aRecord.good = false;

                recordDao.insert(aRecord);
                MainActivity.this.refresh();
                Log.d("TAG","Заводим запись: "+aRecord.id+" : "+aRecord.date+" : "+aRecord.good);


            }
        });


        refresh();
        toast.setText(R.string.bad_toast);
        toast.show();
    }

    private void refresh(){

        BalanceDatabase db = DBContainer.getInstance(MainActivity.this).getDB();
        RecordDao recordDao = db.mRecordDao();

        double good_percent = DBContainer.getInstance(this).good_percent();
        double bad_percent = 1 - good_percent;



        good_percent_label.setText(formatter.format(good_percent*100)+"%");
        bad_percent_label.setText(formatter.format(bad_percent*100)+"%");

        balance_bar.setProgress((int)(bad_percent*1000)-1);

        if((new Date().getTime()-motivator_last_changed)>60000){
            change_motivator();
        }

    }

//    boolean save() {
//        try {
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
//                    openFileOutput(FILENAME, MODE_PRIVATE)));
//
//            bw.write(Storage.get().serial());
//            bw.close();
//            Log.d(TAG, "Файл записан");
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//    boolean load() {
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(
//                    openFileInput(FILENAME)));
//            String str = "";
//            String out = "";
//
//            while ((str = br.readLine()) != null) {
//                out+=str;
//            }
//            Storage.de_serial(out);
//            return true;
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    private void change_motivator(){
        String[] motivators;
        if(Locale.getDefault().getLanguage().contentEquals("ru")){
            motivators = getResources().getStringArray(R.array.motivators_ru);
        }else{
            motivators = getResources().getStringArray(R.array.motivators_en);
        }
        motivation_label.setText(motivators[Wrench.random_int(0, motivators.length-1)]);
        motivator_last_changed = new Date().getTime();
    }
    public static Locale getCurrentLocale(Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            return context.getResources().getConfiguration().getLocales().get(0);
        } else{
            //noinspection deprecation
            return context.getResources().getConfiguration().locale;
        }
    }

    private void getConsentStatus() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(this);
        String[] publisherIds = {"pub-2464895162956927"};
        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {
                // User's consent status successfully updated.
                if (ConsentInformation.getInstance(getBaseContext()).isRequestLocationInEeaOrUnknown()) {
                    switch (consentStatus) {
                        case UNKNOWN:
                            displayConsentForm();
                            break;
                        case PERSONALIZED:
                            initializeAds(true);
                            break;
                        case NON_PERSONALIZED:
                            initializeAds(false);
                            break;
                    }
                } else {
                    Log.d(TAG, "Not in EU, displaying normal ads");
                    initializeAds(true);
                }
            }
            @Override
            public void onFailedToUpdateConsentInfo(String errorDescription) {
                // User's consent status failed to update.
            }
        });
    }
    private void displayConsentForm() {
        URL privacyUrl = null;
        try {
            privacyUrl = new URL(getString(R.string.privacy_policy));
        } catch (MalformedURLException e) {
            Log.e(TAG, "Error processing privacy policy url", e);
        }
        form = new ConsentForm.Builder(this, privacyUrl)
                .withListener(new ConsentFormListener() {
                    @Override
                    public void onConsentFormLoaded() {
                        // Consent form loaded successfully.
                        form.show();
                    }
                    @Override
                    public void onConsentFormOpened() {
                        // Consent form was displayed.
                    }
                    @Override
                    public void onConsentFormClosed(ConsentStatus consentStatus, Boolean userPrefersAdFree) {
                        // Consent form was closed.
                        if (consentStatus.equals(ConsentStatus.PERSONALIZED))
                            initializeAds(true);
                        else
                            initializeAds(false);
                    }
                    @Override
                    public void onConsentFormError(String errorDescription) {
                        // Consent form error. This usually happens if the user is not in the EU.
                        Log.e(TAG, "Error loading consent form: " + errorDescription);
                    }
                })
                .withPersonalizedAdsOption()
                .withNonPersonalizedAdsOption()
                .build();

        form.load();
    }
    private void initializeAds(boolean isPersonalized) {
        // initialize AdMob and configure your ad

        AdRequest adRequest;
        if (isPersonalized) {
            adRequest = new AdRequest.Builder().build();
        } else {
            Bundle extras = new Bundle();
            extras.putString("npa", "1");
            adRequest = new AdRequest.Builder()
                    .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                    .build();
        }
        mInterstitialAd.loadAd(adRequest);

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }

        // load the request into your adView

    }

    public static String formatDateTime(Context context, String timeToFormat) {

        String finalDateTime = "";

        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        Date date = null;
        if (timeToFormat != null) {
            try {
                date = iso8601Format.parse(timeToFormat);
            } catch (ParseException e) {
                date = null;
            }

            if (date != null) {
                long when = date.getTime();
                int flags = 0;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_TIME;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_DATE;
                flags |= android.text.format.DateUtils.FORMAT_ABBREV_MONTH;
                flags |= android.text.format.DateUtils.FORMAT_SHOW_YEAR;

                finalDateTime = android.text.format.DateUtils.formatDateTime(context,
                        when + TimeZone.getDefault().getOffset(when), flags);
            }
        }
        return finalDateTime;
    }

    public static  Long generateUniqueId () {
        long val =  - 1 ;
        do {
            val =  UUID . randomUUID () . getMostSignificantBits ();
        } while (val <  0 );
        return val;
    }
}