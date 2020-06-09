package com.acidcarpet.balance.main;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceManager;

import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.settings.SettingsActivity;
import com.acidcarpet.balance.tutorial.TutorialActivity;
import com.acidcarpet.balance.Wrench;
import com.acidcarpet.balance.data.BalanceDatabase;
import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.data.Record;
import com.acidcarpet.balance.data.RecordDao;

import com.acidcarpet.balance.statistics.StatisticActivity;
import com.google.ads.consent.ConsentForm;
import com.google.ads.consent.ConsentFormListener;
import com.google.ads.consent.ConsentInfoUpdateListener;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.consent.DebugGeography;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    SharedPreferences settings;

    //public static final String APP_PREFERENCES = "root_preferences.xml";

    public static final String APP_PREFERENCES_CONSENT = "consent"; // Согласие на показ персонализированной рекламы
    public static final String APP_PREFERENCES_THEME = "theme"; // Режим отображения ночной темы

    InterstitialAd resume_interstitial_ad;

    ConsentForm form;
    private long motivator_last_changed;

    private static final String TAG = "MAIN_ACTIVITY";

    NumberFormat formatter = new DecimalFormat("#0.00");

    private Toast toast;
    private ImageButton good_button;
    private ImageButton bad_button;
    private TextView good_percent_label;
    private TextView bad_percent_label;
    private TextView motivation_label;
    private TextView author_label;
    private ProgressBar balance_bar;
    private ImageButton tutorial_button;
    private ImageButton statistics_button;
    private ImageButton settings_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        ConsentInformation.getInstance(this).addTestDevice("6A510B5A77801730E22A1AA90F8CB1DF");
        // ConsentInformation.getInstance(this).setDebugGeography (DebugGeography.DEBUG_GEOGRAPHY_EEA);

        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        //System.err.println(settings.getString(APP_PREFERENCES_CONSENT, "Olololololo"));

        if(settings.contains(APP_PREFERENCES_THEME)){
            //System.err.println("Вошли в if ");
            switch (settings.getString(APP_PREFERENCES_THEME, "AUTO")){
                case "DAY": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    //System.err.println("DAY ");
                    break;
                case "NIGHT": AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    //System.err.println("NIGHT");
                    break;
                default:
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
                    //System.err.println("DEFAULT ");
            }
        }else{
            //System.err.println("Вошли в else ");
            //settings.edit().remove(APP_PREFERENCES_THEME);
            settings.edit().putString(APP_PREFERENCES_THEME, "AUTO").apply();
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY);
        }
        //System.err.println("Перед super onCreate ");
        getDelegate().applyDayNight();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        motivator_last_changed = new Date().getTime();

        good_button = (ImageButton) findViewById(R.id.good_button);
        bad_button = (ImageButton) findViewById(R.id.bad_button);
        good_percent_label = (TextView) findViewById(R.id.good_label);
        bad_percent_label = (TextView) findViewById(R.id.bad_label);
        motivation_label = (TextView) findViewById(R.id.motivation_label);
        author_label = (TextView) findViewById(R.id.author_label);
        change_motivator();
        balance_bar = (ProgressBar) findViewById(R.id.balance_bar);

        tutorial_button = (ImageButton) findViewById(R.id.tutorial_button);
        statistics_button= (ImageButton) findViewById(R.id.statistics_button);
        settings_button= (ImageButton) findViewById(R.id.settings_button);

        tutorial_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tutorial_tap();
            }
        });
        statistics_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                statistics_tap();
            }
        });
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settings_tap();
            }
        });

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

        resume_interstitial_ad = new InterstitialAd(this);
        //resume_interstitial_ad.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); test
        resume_interstitial_ad.setAdUnitId("ca-app-pub-2464895162956927/8082340975");

        getConsentStatus();

        refresh();
    }


    @Override
    protected void onResume() {
        super.onResume();

        refresh();
        getConsentStatus();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void good_tap(){
        DBContainer.getInstance(this).add_good_now();
        refresh();
        toast.setText(R.string.good_toast);
        toast.show();
    }
    private void bad_tap(){
        DBContainer.getInstance(this).add_bad_now();
        refresh();
        toast.setText(R.string.bad_toast);
        toast.show();
    }

    private void tutorial_tap(){
        TutorialActivity.activate();
        startActivity(new Intent(this, TutorialActivity.class));
    }
    private void statistics_tap(){
        startActivity(new Intent(this, StatisticActivity.class));
    }
    private void settings_tap(){
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void refresh(){

        //BalanceDatabase db = DBContainer.getInstance(MainActivity.this).getDB();
        //RecordDao recordDao = db.mRecordDao();

        double good_percent;
        double bad_percent;

        if(DBContainer.getInstance(this).good_percent()==0){
            good_percent = 0;
            bad_percent = 0;
        }else{
            good_percent = DBContainer.getInstance(this).good_percent();
            bad_percent = 1 - good_percent;
        }

        good_percent_label.setText(formatter.format(good_percent*100)+"%");
        bad_percent_label.setText(formatter.format(bad_percent*100)+"%");

        balance_bar.setProgress((int)(bad_percent*1000)-1);

        if((new Date().getTime()-motivator_last_changed)>60000){
            change_motivator();
        }

    }

    private void change_motivator(){
        String[] motivators;
        String[] authors;
        if(Locale.getDefault().getLanguage().contentEquals("ru")){
            motivators = getResources().getStringArray(R.array.motivators_ru);
            authors = getResources().getStringArray(R.array.authors_ru);
        }else{
            motivators = getResources().getStringArray(R.array.motivators_en);
            authors = getResources().getStringArray(R.array.authors_en);
        }
        int random_index = Wrench.random_int(0, motivators.length-1);
        motivation_label.setText(motivators[random_index]);
        author_label.setText(authors[random_index]);
        motivator_last_changed = new Date().getTime();
    }

    private void getConsentStatus() {
        ConsentInformation consentInformation = ConsentInformation.getInstance(this);
        String[] publisherIds = {"pub-2464895162956927"};

        consentInformation.requestConsentInfoUpdate(publisherIds, new ConsentInfoUpdateListener() {
            @Override
            public void onConsentInfoUpdated(ConsentStatus consentStatus) {

                if(settings.getString(APP_PREFERENCES_CONSENT, "3").equals("3")){
                    consentStatus = ConsentStatus.UNKNOWN;
                }

                // User's consent status successfully updated. if (ConsentInformation.getInstance(getBaseContext()).isRequestLocationInEeaOrUnknown()) {
                if (ConsentInformation.getInstance(getBaseContext()).isRequestLocationInEeaOrUnknown()) {
                    switch (consentStatus) {
                        case UNKNOWN:
                            try{
                                displayConsentForm();
                            }catch (Exception e){
                                Log.d(TAG, "message!!!"+e.getMessage());
                            }
                            break;
                        case PERSONALIZED:
                            initializeAds(true);
                            //settings.edit().remove(APP_PREFERENCES_CONSENT);
                            settings.edit().putString(APP_PREFERENCES_CONSENT, "1").apply();

                            break;
                        case NON_PERSONALIZED:
                            initializeAds(false);
                            //settings.edit().remove(APP_PREFERENCES_CONSENT);
                            settings.edit().putString(APP_PREFERENCES_CONSENT, "2").apply();

                            break;
                    }
                } else {
                    Log.d(TAG, "Not in EU, displaying normal ads");
                    initializeAds(true);
                    settings.edit().remove(APP_PREFERENCES_CONSENT);
                    settings.edit().putString(APP_PREFERENCES_CONSENT, "1").apply();
                    //settings.edit().commit();
                   // settings.edit().apply();
                    //System.err.println("settings:"+settings.getString(APP_PREFERENCES_CONSENT, "ЫЫЫ"));


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
            privacyUrl = new URL("!!!!!!!!!!!!!!!https://my privacy policy url!!!!!!!!!!!!!!!!!");
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
                        //SharedPreferences settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

                        if (consentStatus.equals(ConsentStatus.PERSONALIZED)) {
                            initializeAds(true);
                            //settings.edit().remove(APP_PREFERENCES_CONSENT);
                            settings.edit().putString(APP_PREFERENCES_CONSENT, "1").apply();
                            //settings.edit().commit();
                            //settings.edit().apply();
                        }else {
                            initializeAds(false);
                            //settings.edit().remove(APP_PREFERENCES_CONSENT);
                            settings.edit().putString(APP_PREFERENCES_CONSENT, "2").apply();
                            //settings.edit().commit();
                            //settings.edit().apply();
                        }
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
        resume_interstitial_ad.loadAd(adRequest);

        if (resume_interstitial_ad.isLoaded()) {
            resume_interstitial_ad.show();
        } else {
            Log.d(TAG, "The interstitial wasn't loaded yet.");
        }

        // load the request into your adView

    }

    public static Long generateUniqueId () {
        long val =  - 1 ;
        do {
            val =  UUID . randomUUID () . getMostSignificantBits ();
        } while (val <  0 );
        return val;
    }
}