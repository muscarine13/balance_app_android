package com.acidcarpet.balance.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.acidcarpet.balance.R;
import com.acidcarpet.balance.data.DBContainer;
import com.acidcarpet.balance.main.MainActivity;

/**
 * Implementation of App Widget functionality.
 */
public class SmallWidget extends AppWidgetProvider {
    public static Toast toast;

    public static final String TAG = "SMALL_WIDGET";

    public static String WIDGET_GOOD_BUTTON = "SMALL.GOOD_BUTTON";
    public static String WIDGET_BAD_BUTTON = "SMALL.BAD_BUTTON";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.small_widget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.small_widget);
        //views.setTextViewText(R.id.appwidget_text, widgetText);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //for (int appWidgetId : appWidgetIds) {
        //updateAppWidget(context, appWidgetManager, appWidgetId);
        //}

        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            //Intent bad_intent = new Intent(context, MainActivity.class);
            //PendingIntent bad_pendingIntent = PendingIntent.getActivity(context, 0, bad_intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.small_widget);

            Intent good_intent = new Intent(WIDGET_GOOD_BUTTON);
            Intent bad_intent = new Intent(WIDGET_BAD_BUTTON);

            PendingIntent good_pendingIntent = PendingIntent.getBroadcast(context, 0, good_intent, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent bad_pendingIntent = PendingIntent.getBroadcast(context, 0, bad_intent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.small_widget_good_button, getPendingSelfIntent(context, WIDGET_GOOD_BUTTON));
            views.setOnClickPendingIntent(R.id.small_widget_bad_button, getPendingSelfIntent(context, WIDGET_BAD_BUTTON));

            //getPendingSelfIntent(context, "ham");
            //views.setOnClickPendingIntent(R.id.small_widget_bad_button, pendingIntent);
            //views.setOnClickPendingIntent(R.id.small_widget_good_button, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);


        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (WIDGET_GOOD_BUTTON.equals(intent.getAction())) {
            //Log.e(TAG, "GOOD BUTTON CLICKED");
            DBContainer.getInstance(context).add_good_now();

            if(toast==null) {
                toast = Toast.makeText(context, R.string.good_toast, Toast.LENGTH_SHORT);
            }else{
                toast.setText(R.string.good_toast);
            }

            toast.show();
        }
        if (WIDGET_BAD_BUTTON.equals(intent.getAction())) {
            //Log.e(TAG, "BAD BUTTON CLICKED");
            DBContainer.getInstance(context).add_bad_now();

            if(toast==null) {
                toast = Toast.makeText(context, R.string.bad_toast, Toast.LENGTH_SHORT);
            }else{
                toast.setText(R.string.bad_toast);
            }

            toast.show();
        }

    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
    Intent intent = new Intent(context, getClass());
    intent.setAction(action);
    return PendingIntent.getBroadcast(context, 0, intent, 0);
}

        }